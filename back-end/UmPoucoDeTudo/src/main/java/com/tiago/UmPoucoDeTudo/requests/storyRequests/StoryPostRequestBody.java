package com.tiago.UmPoucoDeTudo.requests.storyRequests;

import com.tiago.UmPoucoDeTudo.model.Tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryPostRequestBody {

    @NotBlank(message = "O título é obrigatório")
    private String title;

    @NotBlank(message = "A história é obrigatória")
    private String story;

    @NotNull(message = "A etiqueta da história é obrigatória")
    private Tag tag;
    
}
