package com.nhat.demo.entity;


import javax.persistence.*;
import java.util.List;

@Entity
public class RoomTypeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomTypeImageId;
    private String image;
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;



    public RoomTypeImage() {
    }

    public int getRoomTypeImageId() {
        return roomTypeImageId;
    }

    public void setRoomTypeImageId(int roomTypeImageId) {
        this.roomTypeImageId = roomTypeImageId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
