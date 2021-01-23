package com.nhat.demo.repository;

import com.nhat.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    // viet mot ham tra ve mot list cac roomtype.

    @Query("select  R from Room R where R.status = 'AVAILABLE'")
    List<Room> getAvailableRoom();

}
