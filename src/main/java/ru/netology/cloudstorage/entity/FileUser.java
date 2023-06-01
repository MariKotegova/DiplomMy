package ru.netology.cloudstorage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "cloudStorage", name = "files")
public class FileUser {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "file_id")
        private int fileId;

        @Column(name = "file_name", nullable = false, length = 200)
        private String fileName;

        @Column(name = "size", nullable = false)
        private Long size;
        @Column(name = "content")
        private byte[] content;

        @Column(name = "deleted")
        @JsonIgnore
        private int deleted;

        @ManyToOne
        @JoinTable(name = "cloudStorage.users",
                joinColumns = {@JoinColumn(name = "file_id", referencedColumnName = "file_id")})
        private User owner;

        @JsonIgnore
        private String path;

}
