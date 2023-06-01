package ru.netology.cloudstorage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity{

    @Column(nullable = false, length = 212, name = "login")
    private String login;
    @Column(nullable = false, length = 212, name = "password")
    private String password;

    //обозначаем что будет ссылаться на такие то колонки
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

 @OneToMany
    @JoinTable(name = "cloudStorage.files",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<FileUser> files;

    public List<FilrName> getShellFiles() {
        List<FilrName> result = new ArrayList<>();
        files.forEach(x -> result.add(new FilrName(x)));

        return result;
    }

public String toString(){
    return login;
}

}
