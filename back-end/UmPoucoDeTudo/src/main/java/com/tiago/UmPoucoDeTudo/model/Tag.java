package com.tiago.UmPoucoDeTudo.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "tags")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties("stories")
public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Builder.Default
    private LocalDate created_at = LocalDate.now();

    @OneToMany(mappedBy = "tag", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Story> stories;

}
