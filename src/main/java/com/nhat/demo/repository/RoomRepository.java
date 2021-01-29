package com.nhat.demo.repository;

import com.nhat.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query(nativeQuery = true,
    value= "select room.* from room inner join room_type using(room_type_id) " +
            "where room_id NOT IN " +
            "(select distinct room_id from booking_detail bd inner join booking b using(booking_id) " +
            "where ?1 between check_in_date and  date_sub(check_out_date, interval 1 day) or" +
            " ?2 between date_add(check_in_date, interval 1 day) and check_out_date or" +
            " ?1 <= check_in_date and ?2 >= check_out_date or" +
            " ?1 >= check_in_date and ?2 <= check_out_date) " +
            "AND ?3 <= room_type.adult_capacity AND ?4 <= room_type.children_capacity"+
            " order by room_type.unit_price asc")
    List<Room> findAvailableRoom(LocalDate checkInDate, LocalDate checkOutDate, int alduts, int children);


}
