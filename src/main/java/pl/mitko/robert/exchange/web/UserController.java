package pl.mitko.robert.exchange.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import pl.mitko.robert.exchange.dto.ItemsDTO;
import pl.mitko.robert.exchange.entity.UserEntity;
import pl.mitko.robert.exchange.service.SecurityService;
import pl.mitko.robert.exchange.service.UserService;
import pl.mitko.robert.exchange.service.UserStockService;
import pl.mitko.robert.exchange.validator.UserValidator;

import java.util.List;


@Controller
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private SecurityService securityService;

  @Autowired
  private UserValidator userValidator;

  @Autowired
  private UserStockService userStockService;

  @GetMapping("/registration")
  public String registration(Model model) {
    model.addAttribute("userForm", new UserEntity());

    return "registration";
  }

  @PostMapping("/registration")
  public String registration(@ModelAttribute("userForm") UserEntity userForm, BindingResult bindingResult) {
    userValidator.validate(userForm, bindingResult);

    if (bindingResult.hasErrors()) {
      return "registration";
    }

    userService.save(userForm);

    securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

    return "redirect:/welcome";
  }

  @GetMapping("/user/account/balance")
  public String getUserBalance(Model model) {
    model.addAttribute("balance", userService.getUserBalance());

    return "balance";
  }

  @GetMapping("/login")
  public String login(Model model, String error, String logout) {
    if (error != null)
      model.addAttribute("error", "Your username and password is invalid.");

    if (logout != null)
      model.addAttribute("message", "You have been logged out successfully.");

    return "login";
  }

  @GetMapping({"/", "/welcome"})
  public String welcome(Model model) {
    List<ItemsDTO> stocks = userStockService.getUserStocks();
    model.addAttribute("userStocks", stocks);
    List<ItemsDTO> items = userStockService.getAvailableStocks();
    model.addAttribute("availableStocks", items);
    model.addAttribute("balance", userService.getUserBalance());
    return "welcome";
  }
}
