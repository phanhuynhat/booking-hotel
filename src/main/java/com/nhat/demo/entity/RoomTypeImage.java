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
public class RoomTypeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomTypeImageId;
    private String image;
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

}
