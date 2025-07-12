# 使用 Eclipse Temurin JDK 17 Alpine 作为构建镜像（更小）
FROM eclipse-temurin:17-jdk-alpine as builder

# 设置工作目录
WORKDIR /app

# 安装 Maven（使用Alpine包管理器）
RUN apk add --no-cache maven

# 复制 pom.xml 和源代码
COPY pom.xml .
COPY src ./src

# 构建应用（跳过测试以加快构建）
RUN mvn clean package -DskipTests

# 运行时镜像 - 使用更小的Alpine JRE
FROM eclipse-temurin:17-jre-alpine

# 安装字体支持（如果需要）
RUN apk add --no-cache fontconfig ttf-dejavu

# 创建应用用户（安全最佳实践）
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# 设置工作目录
WORKDIR /app

# 从构建阶段复制 JAR 文件
COPY --from=builder /app/target/*.jar app.jar

# 更改文件所有者
RUN chown appuser:appgroup app.jar

# 切换到非root用户
USER appuser

# 暴露端口
EXPOSE 8080

# 优化JVM参数
ENV JAVA_OPTS="-Xmx256m -Xms128m -XX:+UseG1GC -XX:+UseStringDeduplication"

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 