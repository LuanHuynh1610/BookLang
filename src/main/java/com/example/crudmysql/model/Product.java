package com.example.crudmysql.model;

import jakarta.persistence.Entity; // Nếu dùng Spring Boot 3+
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table; // Đặt tên bảng nếu muốn khác với tên class


@Entity // Đánh dấu đây là một entity JPA
@Table(name = "product") // Tên bảng trong database (tuỳ chọn, nếu muốn tên khác 'product')
public class Product {

    @Id // Đánh dấu đây là khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng giá trị ID
    private Integer id;

    // @Column(name = "name") // Tên cột trong database (tuỳ chọn, nếu tên cột khác tên thuộc tính)
    private String name;

    private String brand;

    // @Column(name = "madein")
    private String madein;

    private float price;

    // --- Constructors ---
    public Product() {
        // Constructor mặc định là bắt buộc đối với JPA
    }

    public Product(String name, String brand, String madein, float price) {
        this.name = name;
        this.brand = brand;
        this.madein = madein;
        this.price = price;
    }

    // --- Getters and Setters ---

    public Integer getId() {
        return id;
    }

    // Setter cho ID thường không cần thiết vì nó được tự động sinh ra
    // Tuy nhiên, trong một số trường hợp đặc biệt (như khi load dữ liệu từ DB), nó có thể cần.
    // Nếu bạn không muốn cho phép sửa ID, bạn có thể bỏ dòng này hoặc làm nó private.
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMadein() {
        return madein;
    }

    public void setMadein(String madein) {
        this.madein = madein;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    // --- Optional: toString() method for easy debugging ---
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", madein='" + madein + '\'' +
                ", price=" + price +
                '}';
    }
}
