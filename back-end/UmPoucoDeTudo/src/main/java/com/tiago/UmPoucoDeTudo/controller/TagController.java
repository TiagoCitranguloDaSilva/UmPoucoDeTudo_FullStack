package com.tiago.UmPoucoDeTudo.controller;

import com.tiago.UmPoucoDeTudo.model.User;
import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPostRequestBody;
import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPutRequestBody;
import com.tiago.UmPoucoDeTudo.responses.TagResponse;
import com.tiago.UmPoucoDeTudo.service.TagService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "http://localhost:5173")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<TagResponse>> getAllTags(Authentication authentication) {
        return ResponseEntity.ok(tagService.getAll((User) authentication.getPrincipal()));
    }

    @GetMapping(path = "/getById/{id}")
    public ResponseEntity<TagResponse> getTagById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(tagService.getById(id, (User) authentication.getPrincipal()));
    }

    @PostMapping(path = "/new")
    public ResponseEntity<TagResponse> createNewTag(@RequestBody @Valid TagPostRequestBody tag, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.createTag(tag, (User) authentication.getPrincipal()));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Void> updateTag(@RequestBody @Valid TagPutRequestBody tag, Authentication authentication) {
        tagService.replace(tag, (User) authentication.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id, Authentication authentication) {
        tagService.deleteTagById(id, (User) authentication.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
