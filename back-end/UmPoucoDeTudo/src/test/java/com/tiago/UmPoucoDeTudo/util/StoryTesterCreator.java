package com.tiago.UmPoucoDeTudo.util;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.model.Tag;

public class StoryTesterCreator {

    private final static String DEFAULT_TITLE = "storyTitleTest";
    private final static String DEFAULT_STORY = "storyStoryTest";
    private final static Long DEFAULT_ID = 1L;

    public static Story createStory() {
        return Story.builder()
                .title(DEFAULT_TITLE)
                .story(DEFAULT_STORY)
                .tag(TagTesterCreator.createTag())
                .build();
    }

    public static Story createStory(String tagName) {
        return Story.builder()
                .title(DEFAULT_TITLE)
                .story(DEFAULT_STORY)
                .tag(TagTesterCreator.createTag(tagName))
                .build();
    }

    public static Story createStory(Tag tag) {
        return Story.builder()
                .title(DEFAULT_TITLE)
                .story(DEFAULT_STORY)
                .tag(tag)
                .build();
    }

    public static String getDefaultTitle() {
        return DEFAULT_TITLE;
    }

    public static String getDefaultStory() {
        return DEFAULT_STORY;
    }

    public static Long getDefaultId() {
        return DEFAULT_ID;
    }

}
