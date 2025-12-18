package com.tiago.UmPoucoDeTudo.requests.tagRequests;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagPutRequestBody {
    
    private Long id;
    private String name;
    private LocalDate created_at;

}
