package com.tiago.UmPoucoDeTudo.util;

import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPutRequestBody;

public class TagPutRequestBodyTesterCreator {
    private final static String DEFAULT_NAME = TagTesterCreator.getDefaultName();
    private final static Long DEFAULT_ID = TagTesterCreator.getDefaultId();

    public static TagPutRequestBody createTagPutRequestBody() {
        return TagPutRequestBody.builder().id(DEFAULT_ID).name(DEFAULT_NAME).build();
    }

    public static TagPutRequestBody createTagPutRequestBody(Long id, String name) {
        return TagPutRequestBody.builder().id(id).name(name).build();
    }
}
