package ru.netology.cloudstorage.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.netology.cloudstorage.exeptions.InternalServerException;

import javax.persistence.Column;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
public class FilrName {
    FileUser fileUser;
    private int fileId;
    private String fileName;
    private Long size;
    private byte[] byteContent;
    private File fileContent;

    public FilrName(FileUser content) {
        this.fileId = content.getFileId();
        this.fileName = content.getFileName();
        this.size = content.getSize();
        this.byteContent = content.getContent();
        fileContent = new File(fileName);

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileContent))) {
            bos.write(byteContent);
        } catch (IOException ex) {
            throw new InternalServerException("Internal server exception. Try check filename.");
        }
    }


}
