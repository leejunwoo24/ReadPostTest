package com.example.read_postList.controller;


import com.example.read_postList.common.ApiMappingPattern;
import com.example.read_postList.dto.response.GetUserResponseDto;
import com.example.read_postList.dto.response.ResponseDto;
import com.example.read_postList.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.USER_API)
public class UserController {

    public final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ResponseDto<GetUserResponseDto>> getUserInfo(
            @AuthenticationPrincipal String username
    ){
        ResponseDto<GetUserResponseDto> response = userService.getUserInfo(username);
        return ResponseEntity.ok(response);
    }
}
