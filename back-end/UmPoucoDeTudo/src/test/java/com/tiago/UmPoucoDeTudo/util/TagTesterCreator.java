package com.tiago.UmPoucoDeTudo.util;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TagTesterCreator {

    private static final String DEFAULT_NAME = "tagTest";
    private static final Long DEFAULT_ID = 1L;
    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.now();

    public static Tag createTag() {

        return Tag.builder()
                .name(DEFAULT_NAME)
                .created_at(DEFAULT_CREATED_AT)
                .user(UserTesterCreator.createUser())
                .stories(new ArrayList<>())
                .build();
    }

    public static Tag createTag(User user) {

        return Tag.builder()
                .name(DEFAULT_NAME)
                .created_at(DEFAULT_CREATED_AT)
                .user(user)
                .stories(new ArrayList<>())
                .build();
    }

    public static Tag createTag(User user, List<Story> story) {

        return Tag.builder()
                .name(DEFAULT_NAME)
                .created_at(DEFAULT_CREATED_AT)
                .user(user)
                .stories(story)
                .build();
    }

    public static Tag createTagWithId(User user, List<Story> story) {
        return Tag.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .created_at(DEFAULT_CREATED_AT)
                .user(user)
                .stories(story)
                .build();
    }

    public static Tag createTagWithId() {
        return Tag.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .created_at(DEFAULT_CREATED_AT)
                .user(UserTesterCreator.createUser())
                .stories(new ArrayList<>())
                .build();
    }

    public static Tag createTag(String name, User user) {
        return Tag.builder().name(name).user(user).build();
    }

    public static String getDefaultName() {
        return DEFAULT_NAME;
    }

    public static Long getDefaultId() {
        return DEFAULT_ID;
    }

}
