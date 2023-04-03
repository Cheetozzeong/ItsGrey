from fastapi import Response, UploadFile, APIRouter, HTTPException

import os
import time
import secrets

from pathlib import Path
from datetime import datetime

from PIL import Image, ImageColor, ImageDraw, ImageFont

import cv2
import numpy as np
import tensorflow as tf
import tensorflow_hub as hub

BASE_DIR = Path.cwd()
IMG_DIR = BASE_DIR / 'static' / 'images'

router = APIRouter()
detector = hub.load('core/tf_model').signatures['default']

lower_group = ['Tortoise', 'Magpie', 'Sea turtle', 'Human eye', 'Bird', 'Doll', 'Tick', 'Boy', 'Bear', 'Brown bear',
               'Woodpecker', 'Blue jay', 'Person', 'Bee', 'Bat', 'Popcorn', 'Carnivore', 'Cattle', 'Camel', 'Coat',
               'Suit', 'Cat', 'Bronze sculpture', 'Beetle', 'Fountain', 'Human mouth', 'Dinosaur', 'Ratchet', 'Scarf',
               'Dolphin', 'Mug', 'Human body', 'Food', 'Fruit', 'Fox', 'Human foot', 'Human leg', 'Isopod', 'Grape',
               'Human ear', 'Panda', 'Giraffe', 'Woman', 'Rhinoceros', 'Goldfish', 'Goat', 'Marine invertebrates',
               'Horse', 'Hamster', 'Insect', 'Jaguar', 'Kangaroo', 'Koala', 'Lynx', 'Lavender', 'Human head', 'Lizard',
               'Mammal', 'Mouse', 'Bust', 'Man', 'Mushroom', 'Pitcher', 'Ostrich', 'Girl', 'Plant', 'Penguin',
               'Polar bear', 'Pig', 'Reptile', 'Raven', 'Red panda', 'Rose', 'Rabbit', 'Sculpture', 'Squirrel',
               'Scorpion', 'Snake', 'Sheep', 'Tiger', 'Strawberry', 'Tree', 'Tomato', 'Worm', 'Whale', 'Zebra',
               'Monkey', 'Lion', 'Chicken', 'Eagle', 'Owl', 'Duck', 'Turtle', 'Hippopotamus', 'Crocodile', 'Squid',
               'Spider', 'Deer', 'Frog', 'Dog', 'Elephant', 'Shark', 'Leopard', 'Porcupine', 'Flower', 'Canary',
               'Cheetah', 'Palm tree', 'Fish', 'Lobster', 'Hedgehog', 'Otter', 'Bull', 'Oyster', 'Butterfly',
               'Antelope', 'Moths and butterflies', 'Jellyfish', 'Goose', 'Mule', 'Swan', 'Raccoon', 'Human face',
               'Human arm', 'Falcon', 'Snail', 'Shellfish', 'Dragonfly', 'Sunflower', 'Marine mammal', 'Sea lion',
               'Ladybug', 'Parrot', 'Sparrow', 'Kitchen & dining room table', 'Dog bed', 'Cat furniture', 'Animal',
               'Turkey', 'Lily', 'Human nose', 'Ant', 'Human hand', 'Skunk', 'Teddy bear', 'Shrimp', 'Crab', 'Seahorse',
               'Alpaca', 'Armadillo']


@router.post("/api/v1/itsGrey/makeImage")
async def make_image(file: UploadFile):
    '''
    이미지를 받아 모델을 통한 오브젝트를 탐색, 적절한 수치의 threshold 값을 산출한다.
    이후 해당 값을 기반으로 Canny Edge Detection을 통한 Sketch 이미지를 작성하여 반환한다.
    
    성공적으로 이미지를 작성: 해당 이미지를 Response 후 임시 파일 제거
    문제 발생: 임시 파일을 제거한 뒤 오류를 반환
    '''

    try:
        target_image = get_image(file) # 받은 이미지 경로
        detect_img(detector, target_image) # 이미지 객체 탐색
        detection_class_entities = res["detection_class_entities"] # 결과

        img = cv2.imread(str(target_image))
        is_need_higher_threshold = select_threshold(detection_class_entities[0]) # Higher / Lower Group 반환

        # 조건에 따라 Canny Edge Detection 사용
        if is_need_higher_threshold:
            img = canny_edge_detection(img, 5, 50, 100)
        else:
            img = canny_edge_detection(img, 5, 50, 200)

        # 결과 파일 저장
        result_image = random_img_filename()
        cv2.imwrite(str(IMG_DIR / result_image), img)
        os.remove(target_image)

        # 결과 Response
        with open(str(IMG_DIR / result_image), 'rb') as image_file:
            result = image_file.read()

            return Response(result, media_type='image/jpeg')
    
    # 문제가 발생하면 임시 파일 전부 제거 후 500 Response
    except:
        if target_image and os.path.exists(target_image):
            os.remove(target_image)

        if result_image and os.path.exists(result_image):
            os.remove(result_image)

        raise HTTPException(status_code=500)


def get_image(file: UploadFile):
    '''
    이미지를 BE에 저장하고 경로를 반환하는 함수
    '''

    file_name = random_img_filename()
    local_path = IMG_DIR / file_name

    with open(local_path, 'wb') as file_object:
        file_object.write(file.file.read())

    return local_path


def random_img_filename():
    '''
    난수를 활용한 이미지 파일 이름 생성 함수
    '''

    current_time = datetime.now().strftime('%Y%m%d%H%M%S')
    name = f'{current_time}-{secrets.token_hex(16)}.jpg'

    return name


def draw_bounding_box_on_image(image, ymin, xmin, ymax, xmax, color, font, thickness=4, display_str_list=()):
    '''
    객체의 위치를 표시하는 박스를 그리는 함수
    '''

    draw = ImageDraw.Draw(image)
    im_width, im_height = image.size
    (left, right, top, bottom) = (xmin * im_width, xmax * im_width, ymin * im_height, ymax * im_height)
    draw.line([(left, top), (left, bottom), (right, bottom), (right, top), (left, top)], width=thickness, fill=color)

    display_str_heights = [font.getsize(ds)[1] for ds in display_str_list]
    total_display_str_height = (1 + 2 * 0.05) * sum(display_str_heights)

    if top > total_display_str_height:
        text_bottom = top
    else:
        text_bottom = top + total_display_str_height
    for display_str in display_str_list[::-1]:
        text_width, text_height = font.getsize(display_str)
        margin = np.ceil(0.05 * text_height)
        draw.rectangle([(left, text_bottom - text_height - 2 * margin), (left + text_width, text_bottom)], fill=color)
        draw.text((left + margin, text_bottom - text_height - margin), display_str, fill="black", font=font)
        text_bottom -= text_height - 2 * margin


def draw_boxes(image, boxes, class_names, scores, max_boxes=10, min_score=0.1):
    '''
    데이터 라벨링 함수
    '''

    colors = list(ImageColor.colormap.values())

    try:
        font = ImageFont.truetype("/usr/share/fonts/truetype/liberation/LiberationSansNarrow-Regular.ttf", 25)

    except IOError:
        font = ImageFont.load_default()

    for i in range(min(boxes.shape[0], max_boxes)):
        if scores[i] >= min_score:
            ymin, xmin, ymax, xmax = tuple(boxes[i])
            display_str = "{}: {}%".format(class_names[i].decode("ascii"), int(100 * scores[i]))
            color = colors[hash(class_names[i]) % len(colors)]
            image_pil = Image.fromarray(np.uint8(image)).convert("RGB")
            draw_bounding_box_on_image(image_pil, ymin, xmin, ymax, xmax, color, font, display_str_list=[display_str])
            np.copyto(image, np.array(image_pil))

    return image


def load_img(path):
    '''
    이미지를 Tensorflow를 통해 로드하는 함수
    '''

    img = tf.io.read_file(str(path))
    img = tf.image.decode_jpeg(img, channels=3)

    return img


def detect_img(detector, path):
    '''
    이미지 내의 오브젝트를 판정하는 모델을 통해 결과를 반환하는 함수
    '''

    global res

    img = load_img(path)

    converted_img = tf.image.convert_image_dtype(img, tf.float32)[tf.newaxis, ...]
    # start_time = time.time()
    result = detector(converted_img)
    # end_time = time.time()

    result = {key: value.numpy() for key, value in result.items()}

    # print("Found %d objects." % len(result["detection_scores"]))
    # print("Inference time: ", end_time - start_time)

    res = result


def select_threshold(item):
    '''
    threshold 값을 선택하는 함수
    '''

    if item.decode() in lower_group:
        check_group = True
    else:
        check_group = False

    return check_group


def canny_edge_detection(img, kernel_size=5, low_threshold=50, high_threshold=100):
    '''
    Canny Edge Detection을 통한 이미지를 반환하는 함수
    '''

    gray_img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    blur_img = cv2.GaussianBlur(gray_img, (kernel_size, kernel_size), 0)
    edge_img = cv2.Canny(blur_img, low_threshold, high_threshold)
    edge_img = cv2.bitwise_not(edge_img)

    return edge_img
