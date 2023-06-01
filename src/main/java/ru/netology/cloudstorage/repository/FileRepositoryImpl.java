package ru.netology.cloudstorage.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileRepositoryImpl {
    @Autowired
    private FileManager db;

    private FileRepositoriy fileManager = new FileRepositoriy();
    private final static int NOT_DELETED = 0;
    @PersistenceContext
    private EntityManager entityManager;

    public FileRepositoryImpl(FileRepositoriy fileManager, FileManager db) {
        this.fileManager = fileManager;
        this.db = db;
    }

    public ResponseEntity getList(int limit) {
        List list = db.findAllByDeleted(NOT_DELETED);
        if (limit <= 0) {
            return ResponseEntity.status(400).body("Error input data");
        }
        if (list.size() > 3) {
            return ResponseEntity.status(500).body("Error getting file list");
        }
        return ResponseEntity.status(200).body(list);
    }
}
