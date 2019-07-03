package pl.mitko.robert.exchange.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import pl.mitko.robert.exchange.dto.StocksDTO;

@RestController
@RequestMapping("/")
public class StockController {

  @RequestMapping(value = "/stocks", method = RequestMethod.GET)
  public StocksDTO getCurrentStocks() {
    RestTemplate restTemplate = new RestTemplate();
    String stocksUrl = "http://webtask.future-processing.com:8068/stocks";
    ResponseEntity<StocksDTO> response = restTemplate.getForEntity(stocksUrl, StocksDTO.class);
    return response.getBody();
  }
}
