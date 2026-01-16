package com.tiago.UmPoucoDeTudo.util;

import java.time.LocalDate;

import com.tiago.UmPoucoDeTudo.model.Tag;

public class TagTesterCreator {

    private static final String DEFAULT_NAME = "tagTest";
    private static final Long DEFAULT_ID = 1L;
    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.now();

    public static Tag createTag() {
        return Tag.builder().name(DEFAULT_NAME).created_at(DEFAULT_CREATED_AT).build();
    }

    public static Tag createTag(String name) {
        return Tag.builder().name(name).build();
    }

    public static Tag createTagWithId() {
        return Tag.builder().name(DEFAULT_NAME).id(DEFAULT_ID).created_at(DEFAULT_CREATED_AT).build();
    }

    public static Tag createTagWithId(String name, Long id) {
        return Tag.builder().name(name).id(id).created_at(DEFAULT_CREATED_AT).build();
    }

    public static Tag createTagWithIdAndDate(String name, Long id, LocalDate date) {
        return Tag.builder().name(name).id(id).created_at(date).build();
    }

    public static String getDefaultName() {
        return DEFAULT_NAME;
    }

    public static Long getDefaultId() {
        return DEFAULT_ID;
    }

}
