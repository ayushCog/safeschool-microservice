package com.cognizant.userservice.repository;

import com.cognizant.userservice.entity.User;
import com.cognizant.userservice.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByEmail(String email);

    @Query("SELECT u.id FROM User u")
    public List<Long> findAllIds();

    @Query("SELECT u.id FROM User u WHERE u.role = :role")
    public List<Long> findIdsByRole(@Param("role") String role);

    @Query("SELECT new com.cognizant.userservice.projection.UserProjection(u.userId, u.name, u.role, u.email, u.phone, u.status) FROM User u WHERE u.userId = :id")
    public Optional<UserProjection> findUserById(Long id);
}
