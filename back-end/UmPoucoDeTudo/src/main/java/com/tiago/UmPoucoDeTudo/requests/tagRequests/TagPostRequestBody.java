package com.tiago.UmPoucoDeTudo.requests.tagRequests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagPostRequestBody {

    @NotBlank(message = "O nome é obrigatório")
    private String name;

}

