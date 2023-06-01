package ru.netology.cloudstorage.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.netology.cloudstorage.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class JwtUser implements UserDetails {
    private final Long Id;
    private final String login;
    private final String password;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser(Long id, String login, String password, Collection<? extends GrantedAuthority> authorities) {
        this.Id = id;
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }


       @JsonIgnore
   public Long getId(){
       return Id;
   }
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
       return authorities;
  }

   @Override
   public String getPassword() {
       return password;
   }

   @Override
   public String getUsername() {
       return login;
   }



   @JsonIgnore
   @Override
   public boolean isAccountNonExpired() {
       return true;
   }
   @JsonIgnore
   @Override
   public boolean isAccountNonLocked() {
       return true;
   }
   @JsonIgnore
   @Override
   public boolean isCredentialsNonExpired() {
       return true;
   }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
