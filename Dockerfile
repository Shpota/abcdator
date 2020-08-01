FROM adoptopenjdk/openjdk8:alpine AS BUILDER
COPY . .
RUN ./gradlew build

FROM adoptopenjdk/openjdk8:alpine-jre
COPY --from=BUILDER /build/libs/*all.jar ./app.jar
CMD java -jar app.jar
