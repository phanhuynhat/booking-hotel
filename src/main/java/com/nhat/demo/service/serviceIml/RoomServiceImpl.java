package com.nhat.demo.service.serviceIml;

import com.nhat.demo.entity.Room;
import com.nhat.demo.repository.RoomRepository;
import com.nhat.demo.service.RoomServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomServiceIF {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> getAvailableRoom(LocalDate checkInDate, LocalDate checkOutDate, int adults, int children) {
        return roomRepository.findAvailableRoom(checkInDate, checkOutDate, adults, children);
    }

    @Override
    public Room getRoomById(int id) {
        return roomRepository.findById(id).orElse(null);
    }


    @Override
    public void removeRoom(int roomId) {
        roomRepository.deleteById(roomId);
    }

}
