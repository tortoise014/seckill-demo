# 使用 OpenJDK 17 作为基础镜像
FROM openjdk:17-jdk-slim as builder

# 设置工作目录
WORKDIR /app

# 安装 Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# 复制 pom.xml 和源代码
COPY pom.xml .
COPY src ./src

# 构建应用
RUN mvn clean package -DskipTests

# 运行时镜像
FROM openjdk:17-jre-slim

# 设置工作目录
WORKDIR /app

# 从构建阶段复制 JAR 文件
COPY --from=builder /app/target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"] 