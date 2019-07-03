package pl.mitko.robert.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pl.mitko.robert.exchange.entity.StockEntity;
import pl.mitko.robert.exchange.dto.StocksDTO;
import pl.mitko.robert.exchange.repository.StockRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
  @Autowired
  private StockRepository stockRepository;

  @Scheduled(cron = "0/10 * * ? * *")
  public void update() {
    List<StockEntity> stockEntity = stockRepository.findAll();
    StocksDTO refreshedStocksDTO = getCurrentStocks();
    Date date = refreshedStocksDTO.getDate();
    refreshedStocksDTO.getItems().forEach(i -> {
      if (stockEntity.isEmpty()) {
        StockEntity newStock = new StockEntity();
        newStock.setUnit(i.getUnit());
        newStock.setPrice(i.getPrice());
        newStock.setCode(i.getCode());
        newStock.setName(i.getName());
        newStock.setDate(date);
        stockRepository.save(newStock);
      } else {
        stockEntity.forEach(s -> {
          if (s.getName().equals(i.getName())) {
            Optional<StockEntity> stockEntityToRefresh = stockRepository.findById(s.getId());
            if (stockEntityToRefresh.isPresent()) {
              StockEntity savedEntity = stockEntityToRefresh.get();
              savedEntity.setPrice(i.getPrice());
              savedEntity.setUnit(i.getUnit());
              savedEntity.setDate(date);
              stockRepository.save(savedEntity);
            }
          }
        });
      }
    });
  }

  public StocksDTO getCurrentStocks() {
    RestTemplate restTemplate = new RestTemplate();
    String stocksUrl = "http://webtask.future-processing.com:8068/stocks";
    ResponseEntity<StocksDTO> response = restTemplate.getForEntity(stocksUrl, StocksDTO.class);
    return response.getBody();
  }
}
