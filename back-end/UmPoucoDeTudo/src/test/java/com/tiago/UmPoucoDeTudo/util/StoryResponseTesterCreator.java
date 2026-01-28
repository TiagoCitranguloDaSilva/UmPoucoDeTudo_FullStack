package com.tiago.UmPoucoDeTudo.util;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.responses.StoryResponse;

import java.util.List;

public class StoryResponseTesterCreator {

    public static StoryResponse convertToStoryResponse(Story story) {
        return new StoryResponse(story.getId(), story.getTitle(), story.getStory(), story.getCreated_at());
    }

    public static List<StoryResponse> convertToStoryResponse(List<Story> listStory) {
        return listStory.stream().map(
                story -> new StoryResponse(story.getId(), story.getTitle(), story.getStory(), story.getCreated_at())
        ).toList();
    }

}
