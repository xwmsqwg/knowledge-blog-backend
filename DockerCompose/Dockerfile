# Java 21 运行环境
FROM eclipse-temurin:21.0.13_9-jre-noble

# 统一编码
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

# 创建应用目录
RUN mkdir -p /knowledge_blog
WORKDIR /knowledge_blog

# 复制应用 JAR 文件到容器
COPY knowledge_blog.jar knowledge_blog.jar

# 暴露端口
EXPOSE 8080

# 运行命令
ENTRYPOINT ["java", "-jar", "/knowledge_blog/knowledge_blog.jar"]
