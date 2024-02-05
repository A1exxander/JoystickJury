# Stage 1
FROM node as build

COPY . /app # Not ideal in terms of build performance but best in terms of readability

WORKDIR /app

RUN npm install

RUN npm run build

# Stage 2
FROM nginx:1.24.0

COPY nginx.conf /etc/nginx/nginx.conf

COPY --from=build /app/react-frontend/build /usr/share/nginx/html

EXPOSE 80

ENTRYPOINT["nginx"]

CMD ["-g", "daemon off;"]
