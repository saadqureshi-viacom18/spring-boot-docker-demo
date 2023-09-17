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

3. Interacting with your apis

- GET comments
```
curl --location 'http://localhost:3000/api/v1/comments'
```

- POST comment
```
curl --location 'http://localhost:3000/api/v1/users/f4d27555-ad22-4caa-a1a4-ce12e38249a4/comments' \
    --header 'Content-Type: text/plain' \
    --data 'This is a simple comment.'
```

- DELETE comment
```
curl --location --request DELETE 'http://localhost:3000/api/v1/comments/9ac0df53-4ef8-456b-87f8-c9ba7875cf99' \
    --header 'Content-Type: text/plain' \
    --data '"This is a simple comment"'
```