package pl.mitko.robert.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.mitko.robert.exchange.entity.UserEntity;
import pl.mitko.robert.exchange.repository.RoleRepository;
import pl.mitko.robert.exchange.repository.UserRepository;

import java.util.HashSet;

@Service
public class UserService  {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  private SecurityService securityService;

  public void save(UserEntity user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setRoles(new HashSet<>(roleRepository.findAll()));
    user.setPocket(user.getPocket());
    userRepository.save(user);
  }

  public UserEntity findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public double getUserBalance() {
    String username = securityService.findLoggedInUsername();
    UserEntity userEntity = userRepository.findByUsername(username);

    return userEntity.getPocket();
  }
}
