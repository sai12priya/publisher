package com.tcs.training.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.training.Entity.User;
@Repository
public interface UserRepository extends JpaRepository<User,Integer>  {
	Optional<User> findByUsername(String username);

	Optional<User> findById(Long userId);
}
