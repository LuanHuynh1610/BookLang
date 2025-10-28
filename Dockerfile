# Dùng JDK chính thức của Eclipse Temurin (OpenJDK)
FROM eclipse-temurin:21-jdk

# Đặt thư mục làm việc
WORKDIR /app

# Copy toàn bộ project vào container
COPY . .

# Build app bằng Maven wrapper
RUN ./mvnw clean package -DskipTests

# Chạy file jar
CMD ["java", "-jar", "target/crudmysql-0.0.1-SNAPSHOT.jar"]