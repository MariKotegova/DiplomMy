package ru.netology.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.netology.cloudstorage.dto.RequestDTO;
import ru.netology.cloudstorage.entity.AuthToken;
import ru.netology.cloudstorage.entity.User;
import ru.netology.cloudstorage.repository.FileRepositoryImpl;
import ru.netology.cloudstorage.security.JwtTokenProvider;
import ru.netology.cloudstorage.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")//("/cloud")
public class AuthenticationController {
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final UserService userService;

    private AuthToken authToken;
    FileRepositoryImpl fileRepositoriy;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    //TODO метод для авторизации
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody RequestDTO requestDTO) {
        try {
            String userLogin = requestDTO.getLogin();
            String userPassword = requestDTO.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin, userPassword));
            User user = userService.findByLogin(userLogin);

            System.out.println(userLogin + " + " + userPassword);
            if (user == null) {
                throw new UsernameNotFoundException("Пользователя с таким - " + userLogin + " логином нет");
            }

            String token = jwtTokenProvider.createToken(userLogin, user.getRoles());
            authToken = new AuthToken(token);

            Map<Object, Object> response = new HashMap<>();

            response.put("auth-token", token);

            System.out.println(response);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid login or password");
        }
    }

  @GetMapping("/list")
  public ResponseEntity getList(@RequestHeader("auth-token") String authToken,
                                @RequestParam("limit") int limit) {
      System.out.println("лист");
      fileRepositoriy = new FileRepositoryImpl();
      return ResponseEntity.ok(fileRepositoriy.getList(limit));
 }
    @PostMapping("/logout")
        public ResponseEntity logout() {
        return ResponseEntity.ok("Success logout");
    }
}
