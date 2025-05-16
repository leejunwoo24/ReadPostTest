package com.example.read_postList.dto.response;

import com.example.read_postList.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignInResponseDto {
    private String token;
    private User user;
    private int exprTime;

}
