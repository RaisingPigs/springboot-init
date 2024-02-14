FROM openjdk:8-jre
MAINTAINER Mr.Pan<2905231006@qq.com>

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

# /tmp 目录作为容器数据卷目录，SpringBoot内嵌Tomcat容器默认使用/tmp作为工作目录，任何向 /tmp 中写入的信息不会记录进容器存储层，从而保证容器存储层的无状态化
# 在宿主机的/var/lib/docker目录下创建一个临时文件并把它链接到容器中的/tmp目录
#VOLUME ["/tmp", "/logs"]

#app.jar是自己命名的
ADD target/springboot-init-1.0.0.jar app.jar
# 修改这个文件的访问时间和修改时间为当前时间，而不会修改文件的内容
RUN bash -c "touch /app.jar"

ENV SERVER_PORT 9999
ENV MYSQL_HOST 192.168.79.130
ENV MYSQL_PORT 3306
ENV MYSQL_DB open_api
ENV MYSQL_USERNAME root
ENV MYSQL_PWD root

ENTRYPOINT ["java","-Xms64m","-Xmx64m","-jar","/app.jar","--spring.profiles.active=test"]

EXPOSE 9999
