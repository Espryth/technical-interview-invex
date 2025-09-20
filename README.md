# How To Use This Project

### Dependencies
This project was made to be deployed using Docker and Docker Compose.
So you need to have both installed on your machine. You can find instructions on how to install them here:

* [Install Docker](https://docs.docker.com/get-docker/)
* [Install Docker Compose](https://docs.docker.com/compose/install/)

Internally, the project uses Java 17, Maven, SpringBoot and MySQL.

### Installation
To install the project, you need to clone the repository and then run the following command in the root directory of the project:

```bash
docker compose build
docker compose up -d
```

This will build the Docker image and start the container in detached mode.
The repository has a workflow that deploys the project on a private VPS when you push to the master branch.
