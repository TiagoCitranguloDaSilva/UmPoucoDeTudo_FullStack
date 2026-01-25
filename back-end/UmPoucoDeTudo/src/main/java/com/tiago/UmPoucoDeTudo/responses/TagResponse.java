package com.tiago.UmPoucoDeTudo.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TagResponse {

    private Long id;
    private String name;
    private LocalDate created_at;
    private List<StoryResponse> stories;
    private UserResponse user;

}
