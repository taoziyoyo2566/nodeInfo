FROM openjdk:17-jre-slim
#FROM amazoncorretto:11
LABEL key="taoziyoyo"

WORKDIR /home

COPY target/nodeInfo-0.0.1-SNAPSHOT.jar /home

EXPOSE 8080

#ENV TZ=Asia/Shanghai

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:InitialRAMPercentage=70.0", "-XX:MinRAMPercentage=80.0", "-XX:MaxRAMPercentage=90.0", "-XX:-UseAdaptiveSizePolicy", "-XshowSettings:vm -version", "-jar", "nodeInfo-0.0.1-SNAPSHOT.jar",">>","/home/logs.log"]