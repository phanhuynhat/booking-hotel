package com.nhat.demo.service.serviceIml;

import com.nhat.demo.entity.Booking;
import com.nhat.demo.repository.BookingRepository;
import com.nhat.demo.service.BookingServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingServiceIF {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }
}
