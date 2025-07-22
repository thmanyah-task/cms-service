package com.thmanyah.cms_service.security.repository;

import com.thmanyah.cms_service.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserNameOrEmail(String userName, String email);
    User findByUserName(String userName);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);


}
