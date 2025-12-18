package com.tiago.UmPoucoDeTudo.requests.storyRequests;

import java.time.LocalDate;

import com.tiago.UmPoucoDeTudo.model.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryPostRequestBody {

    private String title;
    private String story;
    private LocalDate created_at;
    private Tag tag;
    
}
