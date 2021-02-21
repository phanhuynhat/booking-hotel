package com.nhat.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingDetailId;
    private int adults;
    private int chidren;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", unique = false, nullable = false)
    private Booking booking;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", unique = false, nullable = false)
    private Room room;

}
