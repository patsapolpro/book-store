package com.volkspace.bookstore.repository;

import com.volkspace.bookstore.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUsernameAndPassword(String username, String passwordHex);

    Users findByUsername(String username);
}
