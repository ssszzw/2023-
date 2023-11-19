FROM node:16-alpine as build-stage

WORKDIR /admim

# COPY .npmrc package.json yarn.lock ./
COPY .npmrc package.json yarn.lock ./

# RUN yarn install --frozen-lockfile

# Set NODE_ENV environment variable
ARG NODE_ENV
ENV NODE_ENV ${NODE_ENV}

RUN yarn install --frozen-lockfile

COPY . .
ARG NODE_ENV=""
RUN env ${NODE_ENV} yarn build

## -- nginx --
FROM nginx:alpine

ENV TZ=Asia/Shanghai

COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build-stage /admim/dist /usr/share/nginx/html

EXPOSE 80