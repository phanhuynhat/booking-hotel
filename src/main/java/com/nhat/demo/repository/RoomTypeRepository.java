package com.nhat.demo.repository;

import com.nhat.demo.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

    @Query(nativeQuery = true,
            value = "select * from room_type where room_type_id >= ?1")
    List<RoomType> findAllRoomType(int id);

    RoomType findByTypeName(String typeName);
}
