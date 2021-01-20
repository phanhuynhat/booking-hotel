package com.nhat.demo.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;
    private int roomNumber;
    private int floor;
    @Enumerated(EnumType.STRING)
    private RoomStatus status = RoomStatus.AVAILABLE;
    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;
    @OneToOne(mappedBy = "room")
    private BookingDetail bookingDetail;
}
