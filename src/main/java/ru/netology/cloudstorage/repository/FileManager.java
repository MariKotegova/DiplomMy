package ru.netology.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.cloudstorage.entity.FileUser;

import java.util.List;

@Repository
public interface FileManager extends JpaRepository<FileUser, Long> {
   List<FileUser> findAllByDeleted(int deleted);
}
