package com.tiago.UmPoucoDeTudo.util;

import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPutRequestBody;

import java.time.LocalDate;

public class TagPutRequestBodyTesterCreator {
    private final static String DEFAULT_NAME = TagTesterCreator.getDefaultName();
    private final static Long DEFAULT_ID = TagTesterCreator.getDefaultId();

    public static TagPutRequestBody createTagPutRequestBody() {
        return TagPutRequestBody.builder().id(DEFAULT_ID).name(DEFAULT_NAME).created_at(LocalDate.now()).build();
    }

    public static TagPutRequestBody createTagPutRequestBody(Long id, String name, LocalDate localDate) {
        return TagPutRequestBody.builder().id(id).name(name).created_at(localDate).build();
    }
}
