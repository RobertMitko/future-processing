package pl.mitko.robert.exchange.dto;

public class SharesDTO {

  private String stockName;
  private Long units;

  public String getStockName() {
    return stockName;
  }

  public void setStockName(String stockName) {
    this.stockName = stockName;
  }

  public Long getUnits() {
    return units;
  }

  public void setUnits(Long units) {
    this.units = units;
  }
}
