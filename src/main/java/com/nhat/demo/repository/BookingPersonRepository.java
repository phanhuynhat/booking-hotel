package com.nhat.demo.repository;

import com.nhat.demo.entity.BookingPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingPersonRepository extends JpaRepository<BookingPerson, Integer> {
}
