FROM openjdk:8
WORKDIR /usrapp/bin

ENV PORT 4567

COPY /target/classes /usrapp/bin/classes
COPY /target/dependency  /usrapp/bin/dependency

CMD ["java", "-cp", "./classes:./dependency/*", "edu.escuelaing.arep.App"]