package com.nhat.demo.repository;

import com.nhat.demo.entity.GuestInRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestInRoomRepository extends JpaRepository<GuestInRoom, Integer> {
}
