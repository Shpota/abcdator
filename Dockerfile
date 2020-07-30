FROM adoptopenjdk/openjdk8:alpine

COPY . .

RUN ./gradlew build