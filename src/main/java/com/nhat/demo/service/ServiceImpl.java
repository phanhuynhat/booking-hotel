package com.nhat.demo.service;

import com.nhat.demo.entity.RoomType;
import com.nhat.demo.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceImpl implements ServiceIF {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public List<RoomType> getAllRoomType() {
        return roomTypeRepository.findAll();
    }
}
