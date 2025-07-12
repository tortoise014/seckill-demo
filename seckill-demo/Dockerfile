FROM openjdk:17-jdk-slim AS builder

WORKDIR /app

# 复制Maven wrapper文件
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn

# 给mvnw执行权限
RUN chmod +x mvnw

# 复制源代码
COPY src src

# 构建应用
RUN ./mvnw clean package -DskipTests

# 运行阶段
FROM openjdk:17-jre-slim

WORKDIR /app

# 复制构建好的JAR文件
COPY --from=builder /app/target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 设置时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"] 