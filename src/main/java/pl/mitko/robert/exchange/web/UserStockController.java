package pl.mitko.robert.exchange.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pl.mitko.robert.exchange.dto.ItemsDTO;
import pl.mitko.robert.exchange.service.UserStockService;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserStockController {

  @Autowired
  private UserStockService userStockService;

  @RequestMapping(value = "/stocks/{stockName}/{units}/buy", method = RequestMethod.POST)
  public ModelAndView buyShares(@PathVariable String stockName, @PathVariable Long units) {
    userStockService.buyShares(stockName, units);
    return new ModelAndView("redirect:" + "/welcome");
  }

  @RequestMapping(value = "/stocks/{stockName}/sell", method = RequestMethod.POST)
  public ModelAndView sellShares(@PathVariable String stockName) {
    userStockService.sellShares(stockName);
    return new ModelAndView("redirect:" + "/welcome");
  }

  @RequestMapping(value = "/user/stocks", method = RequestMethod.GET)
  public String getUserStocks(Model model) {
    model.addAttribute("ustocks", userStockService.getUserStocks());

    return "ustocks";
  }

  @RequestMapping(value = "/stocks/available", method = RequestMethod.GET)
  public String getAvailableStocks(Model model) {
    List<ItemsDTO> items = userStockService.getAvailableStocks();
    model.addAttribute("items", items);

    return "items";
  }


}
