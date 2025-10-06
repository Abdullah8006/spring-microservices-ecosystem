package com.abd.user_service.repository;

import com.abd.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Lock()

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("Delete from User user where user = :user")
    void deleteUser(@Param("user") User user);

}
