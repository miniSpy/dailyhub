package com.dailyhub.repository;

import com.dailyhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import sun.nio.cs.StreamDecoder;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByOpenId(String openId);
}
