package com.nhat.demo.service;

import com.nhat.demo.entity.Room;
import com.nhat.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomServiceIF {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> getAvailableRoom(LocalDate checkInDate, LocalDate checkOutDate, int alduts, int children) {
        return roomRepository.findAvailableRoom(checkInDate, checkOutDate, alduts, children);
    }

}
