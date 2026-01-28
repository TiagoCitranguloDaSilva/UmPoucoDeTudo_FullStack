package com.tiago.UmPoucoDeTudo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "stories")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String story;

    @Column(nullable = false)
    @Builder.Default
    private LocalDate created_at = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

}
