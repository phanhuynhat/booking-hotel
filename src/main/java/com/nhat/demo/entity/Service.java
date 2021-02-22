package com.nhat.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private String serviceName;
    private String description;
    private double unitPrice;
    private String unit;
    @OneToMany(mappedBy = "service")
    private List<Charge> charges = new ArrayList<>();


}
