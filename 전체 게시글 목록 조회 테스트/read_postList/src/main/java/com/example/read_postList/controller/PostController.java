package com.example.read_postList.controller;

import com.example.read_postList.common.ApiMappingPattern;
import com.example.read_postList.dto.request.CreatePostRequestDto;
import com.example.read_postList.dto.response.PostDetailResponseDto;
import com.example.read_postList.dto.response.ResponseDto;
import com.example.read_postList.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.POST_API)
public class PostController {
    private final PostService postService;


    @PostMapping
    public ResponseEntity<ResponseDto<PostDetailResponseDto>> createPost(
            @RequestBody @Valid
            CreatePostRequestDto dto){
        ResponseDto<PostDetailResponseDto> post = postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
}
