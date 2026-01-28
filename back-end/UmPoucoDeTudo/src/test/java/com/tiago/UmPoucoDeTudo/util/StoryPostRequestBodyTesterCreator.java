package com.tiago.UmPoucoDeTudo.util;

import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.requests.storyRequests.StoryPostRequestBody;

public class StoryPostRequestBodyTesterCreator {

    static final String DEFAULT_TITLE = StoryTesterCreator.getDefaultTitle();
    static final String DEFAULT_STORY = StoryTesterCreator.getDefaultStory();


    public static StoryPostRequestBody createStoryPostRequestBody() {
        return StoryPostRequestBody.builder()
                .title(DEFAULT_TITLE)
                .story(DEFAULT_STORY)
                .tag(TagTesterCreator.createTag())
                .build();
    }

    public static StoryPostRequestBody createStoryPostRequestBody(Tag tag) {
        return StoryPostRequestBody.builder()
                .title(DEFAULT_TITLE)
                .story(DEFAULT_STORY)
                .tag(tag)
                .build();
    }

    public static StoryPostRequestBody createStoryPostRequestBody(String title, String story, Tag tag) {
        return StoryPostRequestBody.builder()
                .title(title)
                .story(story)
                .tag(tag)
                .build();
    }

}


