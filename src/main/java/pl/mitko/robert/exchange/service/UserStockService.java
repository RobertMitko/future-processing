package pl.mitko.robert.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.mitko.robert.exchange.dto.ItemsDTO;
import pl.mitko.robert.exchange.entity.StockEntity;
import pl.mitko.robert.exchange.entity.UserEntity;
import pl.mitko.robert.exchange.entity.UserStockEntity;
import pl.mitko.robert.exchange.repository.StockRepository;
import pl.mitko.robert.exchange.repository.UserRepository;
import pl.mitko.robert.exchange.repository.UserStockRepository;
import pl.mitko.robert.exchange.utils.ModelMapper;
import pl.mitko.robert.exchange.utils.ObjectMapperUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.BadRequestException;

@Service
public class UserStockService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private SecurityService securityService;

  @Autowired
  private UserStockRepository userStockRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private ObjectMapperUtils objectMapperUtils;

  public void buyShares(String stockName, Long units) {
    String username = securityService.findLoggedInUsername();
    UserEntity userEntity = userRepository.findByUsername(username);

    StockEntity stockEntity = stockRepository.findByName(stockName);
    if(units > stockEntity.getUnit()) {
      throw new BadRequestException("You can't buy more units that are in stock");
    }

    Double cost = units * stockEntity.getPrice();
    if(cost > userEntity.getPocket()) {
      throw new BadRequestException("You don't have enough money to buy that shares");
    }

    Double userMoney = userEntity.getPocket() - cost;
    userEntity.setPocket(userMoney);
    userRepository.save(userEntity);

    stockEntity.setUnit(stockEntity.getUnit() - units);
    stockRepository.save(stockEntity);

    UserStockEntity savedUserStockEntity;

    Optional<UserStockEntity> userStockEntity = userStockRepository.findByUserIdAndStockId(userEntity.getId(), stockEntity.getId());
    if(!userStockEntity.isPresent()) {
      savedUserStockEntity = new UserStockEntity();
      savedUserStockEntity.setAmount(units);
      savedUserStockEntity.setStockId(stockEntity.getId());
      savedUserStockEntity.setUserId(userEntity.getId());
    } else {
      savedUserStockEntity = userStockEntity.get();
      savedUserStockEntity.setAmount(userStockEntity.get().getAmount() + units);
    }

    userStockRepository.save(savedUserStockEntity);
  }

  public void sellShares(String stockName) {
    String username = securityService.findLoggedInUsername();
    UserEntity userEntity = userRepository.findByUsername(username);
    StockEntity stockEntity = stockRepository.findByName(stockName);

    Optional<UserStockEntity> userStockEntity = userStockRepository.findByUserIdAndStockId(userEntity.getId(), stockEntity.getId());
    if(!userStockEntity.isPresent()) {
      throw new BadRequestException("Not found shares with name " + stockName + " for user with " +
          "name " + username);
    }

    Double pocket = userEntity.getPocket();
    if(userStockEntity.get().getAmount() <= 0) {
      throw new BadRequestException("You don't have enough shares to sell");
    }
    pocket += userStockEntity.get().getAmount() * stockEntity.getPrice();

    userEntity.setPocket(pocket);
    userRepository.save(userEntity);

    userStockRepository.delete(userStockEntity.get());
  }

  public List<ItemsDTO> getUserStocks() {
    String username = securityService.findLoggedInUsername();
    UserEntity userEntity = userRepository.findByUsername(username);

    Optional<List<UserStockEntity>> userStockEntityList = userStockRepository.findAllByUserId(userEntity.getId());
    if(!userStockEntityList.isPresent()) {
      return new ArrayList<>();
    }

    List<ItemsDTO> itemsDTOList = new ArrayList<>();
    userStockEntityList.get().forEach(u -> {
      Long stockId = u.getStockId();
      Optional<StockEntity> stockEntity = stockRepository.findById(stockId);
      if(!stockEntity.isPresent()) {
        throw new BadRequestException("Not found stock entity with id: " + stockId);
      }
      ItemsDTO itemsDTO = new ItemsDTO();
      itemsDTO.setCode(stockEntity.get().getCode());
      itemsDTO.setName(stockEntity.get().getName());
      itemsDTO.setUnit(u.getAmount());
      itemsDTO.setPrice(stockEntity.get().getPrice());
      itemsDTOList.add(itemsDTO);
    });

    return itemsDTOList;
  }

  public List<ItemsDTO> getAvailableStocks() {
    List<StockEntity> stockEntities = stockRepository.findAll();
    return objectMapperUtils.mapAll(stockEntities, ItemsDTO.class);
  }
}
