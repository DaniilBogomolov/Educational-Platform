package ru.itis.models;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "file_info")
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "generated_filename")
    private String storageFileName;

    @Column(name = "original_filename")
    private String originalFileName;

    @Column(name = "size")
    private Long size;

    @Column(name = "type")
    private String type;

    @Transient
    private String url;

    @OneToOne
    @JoinColumn(name = "uploader_id")
    private User owner;

    @PostLoad
    public void generateUrl() {
        this.url = "/files/".concat(storageFileName);
    }
}
