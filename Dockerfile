FROM eclipse-temurin:21-alpine

COPY classes /home/app/classes
COPY dependency/* /home/app/libs/

EXPOSE 8080

ENTRYPOINT ["java", "-cp", "/home/app/libs/*:/home/app/classes/", "fr.hugov.auth.Application"]
