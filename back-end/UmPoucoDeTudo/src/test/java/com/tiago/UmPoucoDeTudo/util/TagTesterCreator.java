package com.tiago.UmPoucoDeTudo.util;

import com.tiago.UmPoucoDeTudo.model.Tag;

public class TagTesterCreator {

    private static final String DEFAULT_NAME = "tagTest";
    private static final Long DEFAULT_ID = 1L;

    public static Tag createTag() {
        return Tag.builder().name(DEFAULT_NAME).build();
    }

    public static Tag createTag(String name) {
        return Tag.builder().name(name).build();
    }

    public static Tag createTagWithId() {
        return Tag.builder().name(DEFAULT_NAME).id(DEFAULT_ID).build();
    }

    public static String getDefaultName() {
        return DEFAULT_NAME;
    }

    public static Long getDefaultId() {
        return DEFAULT_ID;
    }

}
