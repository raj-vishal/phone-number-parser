package com.doordash.interview.phone_number_parser.repository;

import com.doordash.interview.phone_number_parser.model.PhoneAndType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PhoneNumberParserRepo extends JpaRepository<PhoneAndType, Long> {

    @Transactional
    PhoneAndType save(PhoneAndType phoneAndType);
    PhoneAndType findByPhoneNumberAndPhoneType(String number, String phoneType);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE PhoneAndType ph SET ph.occurrences = :occurrences WHERE ph.id = :id")
    void updatePhoneNumber(Long id, int occurrences);
}
