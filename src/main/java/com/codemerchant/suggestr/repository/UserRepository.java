package com.codemerchant.suggestr.repository;

import com.codemerchant.suggestr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
