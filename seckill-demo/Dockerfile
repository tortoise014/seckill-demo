# 构建阶段
FROM maven:3.9-eclipse-temurin-17-alpine AS build

# 设置工作目录
WORKDIR /app

# 先单独复制POM文件（利用Docker缓存）
COPY pom.xml .

# 下载依赖（可缓存）
RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline

# 复制源码并构建
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -DskipTests && \
    mkdir -p /layers && \
    cp /app/target/*.jar /layers/app.jar && \
    cd /layers && \
    java -Djarmode=layertools -jar app.jar extract

# 运行阶段
FROM eclipse-temurin:17-jre-alpine

# 设置时区（中国时区）
ENV TZ=Asia/Shanghai

# 复制分层依赖
COPY --from=build /layers/dependencies/ ./
COPY --from=build /layers/snapshot-dependencies/ ./
COPY --from=build /layers/spring-boot-loader/ ./
COPY --from=build /layers/application/ ./

# 暴露端口（与您的应用配置一致）
EXPOSE 8080

# 启动命令
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]