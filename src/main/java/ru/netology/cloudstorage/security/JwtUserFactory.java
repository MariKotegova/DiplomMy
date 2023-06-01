package ru.netology.cloudstorage.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.netology.cloudstorage.entity.Role;
import ru.netology.cloudstorage.entity.Status;
import ru.netology.cloudstorage.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    public JwtUserFactory(){

    }
    public static JwtUser create (User user){
        return new JwtUser(user.getId(),
                user.getLogin(),
                user.getPassword(),
                mapAuthority(new ArrayList<>(user.getRoles()))

                );
    }

    private static List<GrantedAuthority> mapAuthority(List<Role> userRol){
        return userRol.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
