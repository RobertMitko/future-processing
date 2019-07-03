package pl.mitko.robert.exchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StocksDTO implements Serializable {
  private static final long serialVersionUID = 1L;
  @JsonProperty("publicationDate")
  private Date date;
  @JsonProperty("items")
  private List<ItemsDTO> items;

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public List<ItemsDTO> getItems() {
    return items;
  }

  public void setItems(List<ItemsDTO> items) {
    this.items = items;
  }
}
