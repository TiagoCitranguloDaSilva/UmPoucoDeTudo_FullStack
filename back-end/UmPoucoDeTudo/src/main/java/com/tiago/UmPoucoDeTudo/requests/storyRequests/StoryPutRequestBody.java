package com.tiago.UmPoucoDeTudo.requests.storyRequests;

import java.time.LocalDate;

import com.tiago.UmPoucoDeTudo.model.Tag;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryPutRequestBody {

    @NotNull(message = "O ID é obrigatório")
    @Min(value = 1, message = "ID inválido")
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    private String title;

    @NotBlank(message = "A história é obrigatória")
    private String story;

    @NotNull(message = "A data de criação é obrigatória")
    private LocalDate created_at;

    @NotNull(message = "A etiqueta da história é obrigatória")
    private Tag tag;

}
