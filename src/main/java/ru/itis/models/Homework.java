package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @OneToOne
    @JoinColumn(name = "file_id")
    private FileInfo attachment;

    private boolean isDone;

    @OneToOne
    @JoinColumn(name = "group_id")
    private Room forGroup;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;
}
