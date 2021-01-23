package com.nhat.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomTypeId;
    private String typeName;
    private int adultCapacity;
    private int childrenCapacity;
    private String description;
    private String frontImage;
    private double unitPrice;

    @OneToMany(mappedBy = "roomType")
    private List<RoomTypeImage> roomTypeImages;
    @OneToMany(mappedBy = "roomType")
    private List<Room> rooms;


}
