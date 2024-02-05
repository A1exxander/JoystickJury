# Inherit a base image
FROM mysql:Latest

# Set maintainer
LABEL maintainer="raposoalexander@gmail.com"

# Add ENV variables
ENV MYSQL_DATABASE=joystickjury \
    MYSQL_ROOT_PASSWORD=root \
    MYSQL_PASSWORD=password

# Copy our .sql database creation files into our container, which will automatically be executed inside our /docker-entrypoint-initdb.d/ folder
COPY . /docker-entrypoint-initdb.d/

# Expose a port
EXPOSE 3306
