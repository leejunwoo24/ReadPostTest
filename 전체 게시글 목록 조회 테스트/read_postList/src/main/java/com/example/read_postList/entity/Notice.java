package com.example.read_postList.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notices")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private User user;


}
