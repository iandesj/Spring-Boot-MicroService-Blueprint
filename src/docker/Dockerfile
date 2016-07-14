FROM centos:7
RUN yum update -y && yum install -y \
    epel-release \
    java-1.8.0-openjdk-headless        # Java and epel release
RUN useradd -d /blueprint blueprint
WORKDIR /blueprint
ADD spring-boot-microservice-blueprint.jar /blueprint/
RUN chown -R blueprint:blueprint /blueprint

EXPOSE 8080
USER blueprint
CMD exec java $JAVA_OPTS -jar /blueprint/spring-boot-microservice-blueprint.jar
