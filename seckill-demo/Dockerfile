# 使用 Eclipse Temurin JDK 17 作为构建镜像
FROM eclipse-temurin:17-jdk-alpine as builder

# 设置工作目录
WORKDIR /app

# 安装 Maven
RUN apk add --no-cache maven

# 复制 pom.xml 和源代码
COPY pom.xml .
COPY src ./src

# 构建应用
RUN mvn clean package -DskipTests

# 运行时镜像 - 使用更兼容的基础镜像
FROM eclipse-temurin:17-jre

# 设置工作目录
WORKDIR /app

# 从构建阶段复制 JAR 文件
COPY --from=builder /app/target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"] 