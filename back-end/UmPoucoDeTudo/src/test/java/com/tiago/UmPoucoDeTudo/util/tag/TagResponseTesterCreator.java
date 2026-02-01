package com.tiago.UmPoucoDeTudo.util.tag;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.responses.TagResponse;
import com.tiago.UmPoucoDeTudo.util.user.UserResponseTesterCreator;
import com.tiago.UmPoucoDeTudo.util.story.StoryResponseTesterCreator;

import java.util.ArrayList;
import java.util.List;

public class TagResponseTesterCreator {

    static public TagResponse convertToTagResponse(Tag tag) {
        return new TagResponse(
                tag.getId(),
                tag.getName(),
                tag.getCreated_at(),
                new ArrayList<>(),
                UserResponseTesterCreator.convertToUserResponse(tag.getUser())
        );
    }

    static public List<TagResponse> convertToTagResponse(List<Tag> tags) {
        return tags.stream().map(tag -> new TagResponse(
                tag.getId(),
                tag.getName(),
                tag.getCreated_at(),
                new ArrayList<>(),
                UserResponseTesterCreator.convertToUserResponse(tag.getUser())
        )).toList();
    }

    static public TagResponse convertToTagResponse(Tag tag, List<Story> stories) {
        return new TagResponse(
                tag.getId(),
                tag.getName(),
                tag.getCreated_at(),
                StoryResponseTesterCreator.convertToStoryResponse(stories),
                UserResponseTesterCreator.convertToUserResponse(tag.getUser())
        );
    }


}
