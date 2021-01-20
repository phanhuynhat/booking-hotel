package com.nhat.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class BookingPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingPersonId;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String indentifyNo;
    @OneToOne(mappedBy = "bookingPerson", fetch =FetchType.LAZY )
    private Booking booking;
}
