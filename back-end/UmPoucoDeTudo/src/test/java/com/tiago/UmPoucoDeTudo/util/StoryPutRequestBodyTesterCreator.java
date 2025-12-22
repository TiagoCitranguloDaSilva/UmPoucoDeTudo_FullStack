package com.tiago.UmPoucoDeTudo.util;

import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.requests.storyRequests.StoryPutRequestBody;

public class StoryPutRequestBodyTesterCreator {
    
    static final String DEFAULT_TITLE = StoryTesterCreator.getDefaultTitle();
    static final String DEFAULT_STORY = StoryTesterCreator.getDefaultStory();
    static final Long DEFAULT_ID = StoryTesterCreator.getDefaultId();


    public static StoryPutRequestBody createStoryPutRequestBody(){
        return StoryPutRequestBody.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .story(DEFAULT_STORY)
                .tag(TagTesterCreator.createTag())
            .build();
    }

    public static StoryPutRequestBody createStoryPutRequestBody(Long id, String title, String story, Tag tag){
        return StoryPutRequestBody.builder()
                .id(id)
                .title(title)
                .story(story)
                .tag(tag)
            .build();
    }

}


