package pl.mitko.robert.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.mitko.robert.exchange.entity.StockEntity;

public interface StockRepository extends JpaRepository<StockEntity, Long> {
  StockEntity findByName(String name);
}
