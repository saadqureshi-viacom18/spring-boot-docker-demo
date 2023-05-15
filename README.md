# Spring boot docker demo

## Prerequisites
1. Docker
2. Will to learn

## Get started

```bash
docker run -it --name spring-boot-docker-demo \
    -p 3000:3000 \
    -e PROFILE=dev \
    shoaib1999/spring-boot-docker-demo:v1.0-alpha
```

## Steps

1. Build the image

```bash
docker build -t <your_repository_name>/<image_name>:<tag> .
```

2. Running your application

```bash
docker run -it --name <APP_NAME> \
    -p 3000:3000 \
    -e PROFILE=<dev/prod> \
    <your_repository_name>/<image_name>:<tag>
```

3. Login to your docker hub

```bash
docker login
username: <YOUR_USERNAME>
password: <YOUR_PASSWORD>
```

