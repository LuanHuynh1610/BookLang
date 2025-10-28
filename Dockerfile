# Dùng JDK chính thức của Eclipse Temurin (OpenJDK)
FROM eclipse-temurin:21-jdk

# Đặt thư mục làm việc
WORKDIR /app

# Copy toàn bộ project vào container
COPY . .

# Build app bằng Maven wrapper
RUN ./mvnw clean package -DskipTests

# Cho phép Render chỉ định cổng
ENV PORT=8080
EXPOSE 8080

CMD ["sh", "-c", "java -jar target/crudmysql-0.0.1-SNAPSHOT.jar --server.port=${PORT}"]