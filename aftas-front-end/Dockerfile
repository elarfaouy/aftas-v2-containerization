# first stage for build the app
FROM node:20.11-alpine AS builder

WORKDIR /app

COPY package*.json .

RUN npm install

COPY . .

RUN npm run build --prod

# second stage for copy dist to our web server
FROM nginx:alpine

COPY --from=builder /app/dist/aftas-front-end/browser /usr/share/nginx/html

EXPOSE 80
