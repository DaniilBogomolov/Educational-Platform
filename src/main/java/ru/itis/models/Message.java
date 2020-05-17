package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Message")
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @Column(name = "time_sent")
    private LocalDateTime timeSent;

    @Column(name = "expiring_time")
    private LocalDateTime expiringTime;

    @Column(name = "read")
    private boolean isRead;

    @OneToOne
    @JoinColumn(name = "file_id")
    private FileInfo attachment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room sentFrom;
}
