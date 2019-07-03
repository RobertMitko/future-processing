package pl.mitko.robert.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.mitko.robert.exchange.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

}
