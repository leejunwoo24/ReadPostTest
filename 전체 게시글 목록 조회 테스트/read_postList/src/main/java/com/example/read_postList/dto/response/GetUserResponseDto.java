package com.example.read_postList.dto.response;

import com.example.read_postList.entity.Role;
import com.example.read_postList.entity.User;
import lombok.Getter;

@Getter
public class GetUserResponseDto {
    private Long id;
    private String username;
    private Role role;

    public GetUserResponseDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
