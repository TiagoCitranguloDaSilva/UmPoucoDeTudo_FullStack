package com.tiago.UmPoucoDeTudo.requests.tagRequests;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagPostRequestBody {

    private String name;
    private LocalDate created_at;

}

