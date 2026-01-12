package com.rothurtech.restdemo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;

@EnableTransactionManagement
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "name required")
    private String name;

    @Min(0)
    @Column(nullable = false)
    private Integer age;

    //private BigDecimal salary;
    @Column(nullable = false)
    @Min(0)
    private Double salary;
}
