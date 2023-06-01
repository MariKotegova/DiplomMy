package ru.netology.cloudstorage.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.netology.cloudstorage.entity.FilrName;
import ru.netology.cloudstorage.entity.User;
import ru.netology.cloudstorage.repository.RoleRepository;
import ru.netology.cloudstorage.repository.UsersRepository;
import ru.netology.cloudstorage.security.JwtTokenProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j //для логирования
public class UserService {
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @Autowired
    public UserService(UsersRepository usersRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public List<User> getAll() {
        List<User> result = usersRepository.findAll();
        log.info("Всего зарегистрировано - {} пользователей", result.size());
        return result;
    }

    public User findByLogin(String login) {
        User result = usersRepository.findByLogin(login);
        System.out.println("!!!!!!!!!!!!!" + login);
        log.info("У пользователя - {} логин - {}", result, login);
        return result;
    }

    public User getUserByToken (String token){
        return usersRepository.findByLogin(jwtTokenProvider.getUsername(token));
    }
}