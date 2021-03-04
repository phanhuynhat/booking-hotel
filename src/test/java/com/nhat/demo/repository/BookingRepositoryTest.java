package com.nhat.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookingRepositoryTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private BookingRepository  bookingRepository;

    @Test
    public void findAllTest() {

        LocalDate fromDate = LocalDate.parse("2021-02-20");
        LocalDate toDate = LocalDate.parse("2021-02-22");
        int count = bookingRepository.getBookingBetween(fromDate, toDate).size();
        assertEquals(2, count);
    }

}
