package com.example.read_postList.service;

import com.example.read_postList.common.ResponseMessage;
import com.example.read_postList.dto.request.CreatePostRequestDto;
import com.example.read_postList.dto.response.PostDetailResponseDto;
import com.example.read_postList.dto.response.ResponseDto;
import com.example.read_postList.entity.Post;
import com.example.read_postList.entity.User;
import com.example.read_postList.repository.PostRepository;
import com.example.read_postList.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ResponseDto<PostDetailResponseDto> createPost(CreatePostRequestDto dto) {
        PostDetailResponseDto responseDto = null;


        Post newPost = Post.builder()
                .title(dto.getTitle())
                .content((dto.getContent()))
                .build();

        Post saved = postRepository.save(newPost);

        responseDto = PostDetailResponseDto.builder()
                .id((saved.getId()))
                .title(saved.getTitle())
                .content(saved.getContent())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }
}
