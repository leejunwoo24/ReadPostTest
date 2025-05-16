package com.example.read_postList.service;

import com.example.read_postList.common.ResponseMessage;
import com.example.read_postList.dto.response.GetUserResponseDto;
import com.example.read_postList.dto.response.ResponseDto;
import com.example.read_postList.dto.response.UserSignUpResponseDto;
import com.example.read_postList.entity.User;
import com.example.read_postList.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseDto<GetUserResponseDto> getUserInfo(String username) {
        User user = userRepository.findByUsername(username).orElse(null);

        if(user == null){
            return ResponseDto.setFailed(ResponseMessage.NOT_EXISTS_USER);
        }

        GetUserResponseDto data = new GetUserResponseDto(user);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }
}
