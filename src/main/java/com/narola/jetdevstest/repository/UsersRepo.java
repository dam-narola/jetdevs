package com.narola.jetdevstest.repository;

import com.narola.jetdevstest.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<UserEntity, String> {

    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

}
