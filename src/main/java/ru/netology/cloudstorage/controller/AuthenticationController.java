package ru.netology.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.dto.RequestDTO;
import ru.netology.cloudstorage.entity.AuthToken;
import ru.netology.cloudstorage.entity.FileName;
import ru.netology.cloudstorage.entity.User;
import ru.netology.cloudstorage.repository.FileRepositoryImpl;
import ru.netology.cloudstorage.security.JwtTokenProvider;
import ru.netology.cloudstorage.service.UserService;

import javax.ws.rs.Consumes;
import java.io.IOException;
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
            //дай юзера по логину
            User user = userService.findByLogin(userLogin);
           // System.out.println(userLogin + " + " + userPassword);
            if (user == null) {
                throw new UsernameNotFoundException("Пользователя с таким - " + userLogin + " логином нет");
            }
            String token = jwtTokenProvider.createToken(userLogin, user.getRoles());
            authToken = new AuthToken(token);
            Map<Object, Object> response = new HashMap<>();
            response.put("auth-token", token);
//            System.out.println(response);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @PostMapping("/logout")
        public ResponseEntity logout() {
        return ResponseEntity.ok("Success logout");
    }

    @GetMapping("/list")
    public ResponseEntity getList(@RequestParam("auth-token") String token,
                                  @RequestParam int limit) {
        System.out.println(token);
        return fileRepositoriy.getList(limit);
    }

    @PostMapping("/file")
    @Consumes(value = "multipart/form-data")
    public ResponseEntity addFile(@RequestParam("auth-token") String token,
                                  @RequestParam String filename,
                                  @RequestBody MultipartFile file) throws IOException {
        System.out.println(token);
        return fileRepositoriy.addFile(filename, file.getSize(), file.getBytes());
    }
     @PutMapping("/file")
    @Consumes(value = "application/json")
    public ResponseEntity changeName(@RequestParam("auth-token") String token,
                                     @RequestParam String filename,
                                     @RequestBody FileName newName) {
        return fileRepositoriy.changeName(filename, newName.getFileName());
    }

    @DeleteMapping("/file")
    public ResponseEntity deleteFile(@RequestParam("auth-token") String token,
                                     @RequestParam String filename) {
        System.out.println(token);
        return fileRepositoriy.deleteFile(filename);
    }

    @GetMapping("/file")
    public ResponseEntity get(@RequestParam("auth-token") String token,
                              @RequestParam String filename) throws IOException {
        System.out.println(token);
        return fileRepositoriy.getFile(filename);
    }
}
