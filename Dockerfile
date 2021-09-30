FROM openjdk:11
ARG user=spring
ARG group=spring
ARG uid=1000
ARG gid=1000
ARG JAR_FILE=target/*.jar

RUN groupadd -g ${gid} ${group} && useradd -u ${uid} -g ${group} -s /bin/sh ${user}
USER ${user}

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=localdocker"]