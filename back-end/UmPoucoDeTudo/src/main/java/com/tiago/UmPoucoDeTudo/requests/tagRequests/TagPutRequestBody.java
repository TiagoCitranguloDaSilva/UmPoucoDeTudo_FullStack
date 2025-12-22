package com.tiago.UmPoucoDeTudo.requests.tagRequests;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagPutRequestBody {
    
    @NotNull(message = "O ID é obrigatório")
    @Min(value = 1, message = "ID inválido")
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @NotNull(message = "A data de criação é obrigatória")
    private LocalDate created_at;

}
