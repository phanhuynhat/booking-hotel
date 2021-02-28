package com.nhat.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceId;
    @NotEmpty(message = "Tên dịch vụ không được để trống")
    private String serviceName;
    @NotEmpty(message = "Tên mô tả không được để trống")
    @Size(min = 150, max =400 , message
            = "Mô tả phải từ 150 đến 400 ký tự")
    @Column(columnDefinition = "TEXT")
    @NotEmpty(message = "Tên dịch vụ không được để trống")
    private String description;
    @Positive(message = "Đơn giá phải làm một số dương")
    private double unitPrice;
    @NotBlank(message = "Đơn vị tính không được để trống")
    private String unit;
    @OneToMany(mappedBy = "service")
    private List<Charge> charges = new ArrayList<>();


}
