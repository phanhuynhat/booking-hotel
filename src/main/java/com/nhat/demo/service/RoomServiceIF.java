package com.nhat.demo.service;

import com.nhat.demo.entity.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomServiceIF {

    List<Room> getAvailableRoom(LocalDate checkInDate, LocalDate checkOutDate, int adults, int children);

    Room getRoomById(int roomId);

    void removeRoom(int roomId);

}
