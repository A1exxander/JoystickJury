LABEL app = "JoystickJury Frontend"
LABEL version="1.0"
LABEL maintainer = "Alexander Raposo <raposoalexander@gmail.com>"

# Stage 1
FROM node as build

COPY . /app # Not ideal in terms of build performance but best in terms of readability & simplicity.

WORKDIR /app/src

RUN npm install

RUN npm run build

# Stage 2
FROM nginx:1.24.0

COPY nginx.conf /etc/nginx/nginx.conf

COPY --from=build /app/src/build /usr/share/nginx/html

EXPOSE 80

ENTRYPOINT["nginx"]

CMD ["-g", "daemon off;"]
