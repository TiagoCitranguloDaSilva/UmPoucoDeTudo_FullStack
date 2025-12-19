package com.tiago.UmPoucoDeTudo.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JsonIgnoreProperties("stories")
    private Tag tag;

}
