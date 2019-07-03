package pl.mitko.robert.exchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class ItemsDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("publicationDate")
  private Date date;

  @JsonProperty("name")
  private String name;
  @JsonProperty("code")
  private String code;
  @JsonProperty("unit")
  private Long unit;
  @JsonProperty("price")
  private Double price;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public Long getUnit() {
    return unit;
  }

  public void setUnit(Long unit) {
    this.unit = unit;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
