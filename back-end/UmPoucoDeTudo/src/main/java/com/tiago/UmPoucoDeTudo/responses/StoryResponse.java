package com.tiago.UmPoucoDeTudo.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StoryResponse {
    private Long id;
    private String title;
    private String story;
    private Long tagId;
    private LocalDate created_at;
}
