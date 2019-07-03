package pl.mitko.robert.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.mitko.robert.exchange.entity.UserStockEntity;

import java.util.List;
import java.util.Optional;

public interface UserStockRepository extends JpaRepository<UserStockEntity, Long> {
  Optional<UserStockEntity> findByUserIdAndStockId(Long userId, Long stockId);
  Optional<List<UserStockEntity>> findAllByUserId(Long userId);

}
