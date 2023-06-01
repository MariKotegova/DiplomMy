package ru.netology.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.cloudstorage.entity.User;

public interface UsersRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
