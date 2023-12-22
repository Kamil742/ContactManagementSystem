package com.application.contactmanagementsystem.repository;

import com.application.contactmanagementsystem.entity.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserContact, Long> {

    boolean existsByContactNo(String emailId);
}
