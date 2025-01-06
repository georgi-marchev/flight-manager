#
# Build stage
#
FROM eclipse-temurin:17-jdk-jammy AS build
ARG HOME_DIR=/usr/app
ARG BACKEND_DIR=${HOME_DIR}/backend
ENV HOME=${HOME_DIR}

RUN mkdir -p $HOME

WORKDIR $HOME
ADD . $HOME

WORKDIR $BACKEND_DIR

RUN --mount=type=cache,target=/root/.m2 ./mvnw -f ${BACKEND_DIR}/pom.xml clean package

#
# Package stage
#
FROM eclipse-temurin:17-jre-jammy
ARG JAR_FILE=/usr/app/backend/target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/runner.jar"]