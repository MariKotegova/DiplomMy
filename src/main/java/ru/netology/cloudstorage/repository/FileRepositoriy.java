package ru.netology.cloudstorage.repository;


import lombok.Getter;
import ru.netology.cloudstorage.entity.FileUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class FileRepositoriy {
    private final String path = "C:\\Diplom\\";

    private final String deleted = "(deleted)";

    // оздать файл
    public void createFile(String filename, byte[] bytes) {
        File file = new File(path + filename);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //изменить имя файла
    public boolean changeName(String newFilename, String path) {
        File oldFile = new File(path);
        if (!oldFile.exists()) {
            return false;
        }
        File file = new File(path + newFilename);
        return oldFile.renameTo(file);
    }

    public byte[] getFile(FileUser file) throws IOException {
        Path path = Path.of(file.getPath());
        return Files.readAllBytes(path);
    }

    public boolean delete(String filename, String path) {
        File oldFile = new File(path);
        if (!oldFile.exists()) {
            return false;
        }
        File file = new File(path + deleted + filename);
        System.out.println(" delete" + file);
        return oldFile.renameTo(file);
    }
}

