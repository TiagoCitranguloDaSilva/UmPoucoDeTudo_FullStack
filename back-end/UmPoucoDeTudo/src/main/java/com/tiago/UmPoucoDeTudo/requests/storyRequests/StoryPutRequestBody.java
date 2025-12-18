package com.tiago.UmPoucoDeTudo.requests.storyRequests;

import java.time.LocalDate;

import com.tiago.UmPoucoDeTudo.model.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryPutRequestBody {

    private Long id;
    private String title;
    private String story;
    private LocalDate created_at;
    private Tag tag;

}
