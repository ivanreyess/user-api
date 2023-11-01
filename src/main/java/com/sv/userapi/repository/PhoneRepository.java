package com.sv.userapi.repository;

import com.sv.userapi.domain.Phone;
import com.sv.userapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA repository for the Phone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhoneRepository extends JpaRepository<Phone, UUID> {

    List<Phone> getPhoneByUser(User user);

}
