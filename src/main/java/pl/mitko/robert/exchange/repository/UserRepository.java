package pl.mitko.robert.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.mitko.robert.exchange.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findByUsername(String username);
}
