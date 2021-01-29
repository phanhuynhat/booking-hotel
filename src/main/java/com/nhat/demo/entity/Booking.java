package com.nhat.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;
    private String bookingCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bookingDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cancelDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkInDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOutDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    private double bookingPrice;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", nullable = true, unique = false)
    private Promotion promotion;

    @OneToMany(mappedBy = "booking")
    private List<BookingDetail> bookingDetails;

    @OneToMany(mappedBy = "booking")
    private List<Charge> charges;

    @OneToOne
    @JoinColumn(name = "invoice_id", nullable = true, unique = true)
    private Invoice invoice;

    @OneToOne
    @JoinColumn(name = "booking_person_id")
    private BookingPerson bookingPerson;

    @OneToMany(mappedBy = "booking")
    private List<Transaction> transactions;


}
