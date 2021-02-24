package com.nhat.demo.service;

import com.nhat.demo.model.BookingDetailDTO;

import java.util.List;

public interface BookingDetailServiceIF {

    List<BookingDetailDTO> getBookingDetalDTO(String PromotionCode);


}
