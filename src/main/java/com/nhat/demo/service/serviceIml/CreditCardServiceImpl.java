package com.nhat.demo.service.serviceIml;

import com.nhat.demo.entity.CreditCard;
import com.nhat.demo.model.BookingCart;
import com.nhat.demo.repository.CreditCardRepository;
import com.nhat.demo.service.CreditCardServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardServiceImpl implements CreditCardServiceIF {
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private BookingCart bookingCart;

    @Override
    public String ValidateCart(CreditCard creditCard) {
        CreditCard crdCard = creditCardRepository.findByCardNumber(creditCard.getCardNumber());
        //Nếu sai sai so tai khoan
        if (crdCard == null) {
            return "not found";
        }

        // neu dung so tai khoan kiểm tra tiếp 3 thông tin còn lại
        if (!crdCard.equals(creditCard)) {
            return "not match";
        }
        // neu dung so tk, dung 3 thong tin con lai
        //kiem tra tiep tai khoan con du tien khong?

        double total = bookingCart.calculateTotal();
        if (crdCard.getBalance() < total) {
            return "not enough";
        }

        return "ok";

    }

}
