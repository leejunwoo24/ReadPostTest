package com.example.read_postList.controller;

import com.example.read_postList.common.ApiMappingPattern;
import com.example.read_postList.dto.request.UserSignInRequestDto;
import com.example.read_postList.dto.request.UserSignUpRequestDto;
import com.example.read_postList.dto.response.ResponseDto;
import com.example.read_postList.dto.response.UserSignInResponseDto;
import com.example.read_postList.dto.response.UserSignUpResponseDto;
import com.example.read_postList.service.AuthService;
import com.example.read_postList.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.AUTH_API)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<UserSignUpResponseDto>> signUp(
            @Valid @RequestBody UserSignUpRequestDto dto
    ){
        System.out.println("=== 회원가입 요청 도착 ===");
        ResponseDto<UserSignUpResponseDto> response = authService.SignUp(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDto<UserSignInResponseDto>> login(@Valid @RequestBody UserSignInRequestDto dto) {
        ResponseDto<UserSignInResponseDto> response = authService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
