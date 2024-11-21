package com.example.demo.repositories.interfaces;

import com.example.demo.enums.UserRole;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findByRole(UserRole role);
    List<User> findByUsernameContaining(String substring);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findUserCustuomQuery(@Param("username")String username);

    @Query(value = "SELECT * FROM users WHERE username LIKE %:keyword%", nativeQuery = true)
    List<User> searchUsersByKeyword(@Param("keyword")String keyword);
}
