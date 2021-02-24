package com.nhat.demo.repository;

import com.nhat.demo.entity.BookingDetail;
import com.nhat.demo.model.BookingDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, Integer> {




    @Query(value = "select room_number as roomNumber, type_name as roomType, " +
            " adults as adults, chidren as children, (datediff(check_out_date, check_in_date) * unit_price) as subtotal " +
            "from booking " +
            "inner join booking_detail  using(booking_id) " +
            "inner join room using(room_id) " +
            "inner join room_type using(room_type_id) " +
            "where booking_code = ?1", nativeQuery = true)
    List<BookingDetailDTO> getBookingDetailDTO(String promotionCode);

}
