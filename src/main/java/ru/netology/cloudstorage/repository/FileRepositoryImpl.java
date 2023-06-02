package ru.netology.cloudstorage.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.cloudstorage.entity.FileUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
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

    @Transactional
    public ResponseEntity addFile(String filename, long size, byte[] byteArr) {
        if (size <= 0 || byteArr.length == 0 || filename.length() == 0) {
            return ResponseEntity.status(400).body("Error input data");
        }
        FileUser file = db.findByFilenameAndDeleted(filename, NOT_DELETED);
        if (file == null) {
            FileUser downloadFile = FileUser.builder()
                    .filename(filename)
                    .size((int) size)
                    .path(fileManager.getPath() + filename)
                    .build();
            entityManager.persist(downloadFile);
            if (downloadFile == null) {
                return ResponseEntity.status(500).body("Error adding file");
            }
            fileManager.createFile(filename, byteArr);
        }
        return ResponseEntity.ok("Success upload");
    }

    @Transactional
    public ResponseEntity changeName(String oldName, String newName) {
        FileUser file = db.findByFilenameAndDeleted(oldName, NOT_DELETED);
        if (file == null) {
            return ResponseEntity.status(400).body("Error input data");
        }
        String oldPath = file.getPath();
        if (!fileManager.changeName(newName, oldPath)) {
            return ResponseEntity.status(500).body("Error upload file");
        }
        file.setFilename(newName);
        file.setPath(fileManager.getPath() + newName);
        entityManager.persist(file);
        return ResponseEntity.ok("Success upload");
    }

    @Transactional
    public ResponseEntity deleteFile(String filename) {
        FileUser file = db.findByFilenameAndDeleted(filename, NOT_DELETED);
        if (file == null) {
            return ResponseEntity.status(400).body("Error input data");
        }
        String path = file.getPath();
        if (!fileManager.delete(filename, path)) {
            return ResponseEntity.status(500).body("Error delete file");
        }
        int DELETED = 1;
        file.setDeleted(DELETED);
        file.setPath(fileManager.getPath() + fileManager.getDeleted() + filename);
        entityManager.persist(file);
        return ResponseEntity.ok("Success deleted");
    }

    public ResponseEntity getFile(String filename) throws IOException {
        FileUser file = db.findByFilenameAndDeleted(filename, NOT_DELETED);
        if (file == null) {
            return ResponseEntity.status(400).body("Error input data");
        }
        byte[] fileBytes = fileManager.getFile(file);
        if (fileBytes.length == 0) {
            return ResponseEntity.status(500).body("Error upload file");
        }
        return ResponseEntity.ok(fileBytes);
    }
}
