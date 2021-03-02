package com.nhat.demo.repository;

import com.nhat.demo.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Booking findByBookingCode(String bookingCode);

    @Query(nativeQuery = true, value = "")
    double getTotalByBookingCode(String bookingCode);

    @Query(nativeQuery = true, value = "select datediff(check_out_date, check_in_date) " +
            "as night from booking where booking_code =?1")
    int getNightByBookingCode(String bookingCode);


    void deleteByBookingCode(String bookingCode);
    @Query(nativeQuery = true, value = "select * from booking " +
            "where curdate() between check_in_date and check_out_date")
    List<Booking> findCurrentStayBooking();

}
