package com.example.rowersi.rest;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.rowersi.dao.UserDao;
import com.example.rowersi.entity.User;
import com.example.rowersi.security.UserDetailsManagerImplementation;

@RestController
@RequestMapping("api/v1")
public class UsersController {

  private UserDao userDao;
  private UserDetailsManagerImplementation userDetailsManagerImplementation;

  public UsersController(UserDao userDao,
      UserDetailsManagerImplementation userDetailsManagerImplementation) {
    this.userDao = userDao;
    this.userDetailsManagerImplementation = userDetailsManagerImplementation;
  }

  @GetMapping("/admin/users")
  public List<User> getUsers() {

    return userDao.getUsers();
  };

  @GetMapping("/users")
  public String getUser(@RequestParam String username) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);

    UserDetails userDetails = this.userDetailsManagerImplementation.getUser(username);
    String password = userDetails.getPassword();
    System.out.println(password);
    return "" + password + " ====== " + encoder.matches("1q2w3e4r", password);
  }

  @PostMapping("/users")
  public void testMethod(@RequestBody UserCredentials userCredentials) {
    String username = userCredentials.username();
    this.userDetailsManagerImplementation
        .createUser(userCredentials.username, userCredentials.password);
  }

  public record UserCredentials(String username, String password) {
  }

}
