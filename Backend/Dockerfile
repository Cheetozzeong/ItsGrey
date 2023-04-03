FROM debian
RUN apt-get update

# 작업 디렉토리 생성 및 설정
WORKDIR /

# Tensorflow 및 OpenGL 기반 소스 설치
RUN apt-get install -y python3-dev python3-pip libgl1-mesa-glx libglib2.0-0
RUN pip install tensorflow==2.10.0

# 필요한 라이브러리 및 패키지 설치
COPY requirements.txt .
RUN pip install -r requirements.txt

# 소스코드 복사
COPY . .

# 컨테이너가 실행될 때 실행할 명령어 설정
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]
