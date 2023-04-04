import os
import tensorflow as tf
import tensorflow_hub as hub
import tempfile
import cv2
import numpy as np
from PIL import Image
from PIL import ImageColor
from PIL import ImageDraw
from PIL import ImageFont
from PIL import ImageOps
import time
import math

# module_handle = "https://tfhub.dev/google/faster_rcnn/openimages_v4/inception_resnet_v2/1"
module_handle = "https://tfhub.dev/google/openimages_v4/ssd/mobilenet_v2/1"
detector = hub.load(module_handle).signatures['default']

source_img = os.path.join(os.getcwd(), 'data', 'images', 'beans.jpg')

global res
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


def download_and_resize_image(url, new_width=256, new_height=256, display=False):
    _, filename = tempfile.mkstemp(suffix=".jpg")
    pil_image = Image.open(url)
    pil_image = ImageOps.fit(pil_image, (640, 480), Image.ANTIALIAS)
    pil_image_rgb = pil_image.convert("RGB")
    pil_image_rgb.save(filename, format="JPEG", quality=90)
    print("Image downloaded to %s." % filename)
    if display:
        cv2.imshow('image', pil_image)
    return filename


def draw_bounding_box_on_image(image, ymin, xmin, ymax, xmax, color, font, thickness=4, display_str_list=()):
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
    colors = list(ImageColor.colormap.values())

    try:
        font = ImageFont.truetype("/usr/share/fonts/truetype/liberation/LiberationSansNarrow-Regular.ttf", 25)
    except IOError:
        print("Font not found, using default font.")
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
    img = tf.io.read_file(path)
    img = tf.image.decode_jpeg(img, channels=3)
    return img


def run_detector(detector, path):
    global res
    img = load_img(path)

    converted_img = tf.image.convert_image_dtype(img, tf.float32)[tf.newaxis, ...]
    start_time = time.time()
    result = detector(converted_img)
    end_time = time.time()

    result = {key: value.numpy() for key, value in result.items()}

    print("Found %d objects." % len(result["detection_scores"]))
    print("Inference time: ", end_time - start_time)

    image_with_boxes = draw_boxes(
        img.numpy(), result["detection_boxes"],
        result["detection_class_entities"], result["detection_scores"])

    res = result
    cv2.imshow('image', image_with_boxes)


def detect_img(image_url):
    start_time = time.time()
    image_path = download_and_resize_image(image_url, 640, 480)
    run_detector(detector, image_path)
    end_time = time.time()
    print("Inference time:", end_time - start_time)


def canny_edge_detection(img, kernel_size=5, low_threshold=50, high_threshold=100):
    gray_img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    blur_img = cv2.GaussianBlur(gray_img, (kernel_size, kernel_size), 0)
    edge_img = cv2.Canny(blur_img, low_threshold, high_threshold)
    edge_img = cv2.bitwise_not(edge_img)
    return edge_img


def canny_each_object(original_img):
    copy_img = original_img.copy()
    copy_img = canny_edge_detection(copy_img, 5, 50, 100)
    im_width, im_height = (Image.fromarray(np.uint8(original_img))).size

    i = 0
    while i < 100 and res['detection_scores'][i] >= 0.1:
        ymin, xmin, ymax, xmax = res['detection_boxes'][i]
        (left, right, bottom, top) = (
            int(xmin * im_width), math.ceil(xmax * im_width), int(ymin * im_height), math.ceil(ymax * im_height)
        )

        roi = original_img[bottom:top, left:right].copy()

        if res['detection_class_entities'][i].decode() in lower_group:
            edges = canny_edge_detection(roi, 5, 50, 100)
        else:
            edges = canny_edge_detection(roi, 5, 50, 200)

        edges_resized = cv2.resize(edges, ((right - left), (top - bottom)))
        copy_img[bottom:top, left:right] = edges_resized
        i += 1
    return copy_img


detect_img(source_img)

img = cv2.imread(source_img)
final_img = canny_each_object(img)
cv2.imshow('Final', final_img)

cv2.imwrite(os.path.join("data", "images", "final_img.jpg"), final_img)
# 키보드 입력 대기
cv2.waitKey(0)

# 모든 창 닫기
cv2.destroyAllWindows()
