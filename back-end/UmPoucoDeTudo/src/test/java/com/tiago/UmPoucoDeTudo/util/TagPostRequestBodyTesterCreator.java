package com.tiago.UmPoucoDeTudo.util;

import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPostRequestBody;

public class TagPostRequestBodyTesterCreator {
    private final static String DEFAULT_NAME = TagTesterCreator.getDefaultName();

    public static TagPostRequestBody createTagPostRequestBody(){
        return TagPostRequestBody.builder().name(DEFAULT_NAME).build();
    }

    public static TagPostRequestBody createTagPostRequestBody(String name){
        return TagPostRequestBody.builder().name(name).build();
    }

}
