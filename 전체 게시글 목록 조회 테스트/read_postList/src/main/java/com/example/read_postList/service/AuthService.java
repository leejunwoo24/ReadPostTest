package com.example.read_postList.service;

import com.example.read_postList.common.ResponseMessage;
import com.example.read_postList.dto.request.UserSignInRequestDto;
import com.example.read_postList.dto.request.UserSignUpRequestDto;
import com.example.read_postList.dto.response.ResponseDto;
import com.example.read_postList.dto.response.UserSignInResponseDto;
import com.example.read_postList.dto.response.UserSignUpResponseDto;
import com.example.read_postList.entity.Role;
import com.example.read_postList.entity.User;
import com.example.read_postList.provider.JwtProvider;
import com.example.read_postList.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    public ResponseDto<UserSignUpResponseDto> SignUp(@Valid UserSignUpRequestDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();
        Role role = dto.getRole();

        UserSignUpResponseDto  data = null;
        User user = null;

        if(!password.equals(confirmPassword)){
            return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD);
        }

        if(userRepository.existsByUsername(username)){

            return ResponseDto.setFailed(ResponseMessage.EXIST_DATA);
        }

        String encodePassword = bCryptPasswordEncoder.encode(password);

        user = User.builder()
                .username(username)
                .password(encodePassword)
                .role(role)
                .build();

        userRepository.save(user);

        data = new UserSignUpResponseDto(user);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    public ResponseDto<UserSignInResponseDto> login(@Valid UserSignInRequestDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        UserSignInResponseDto data = null;
        User user = null;

        int exprTime = jwtProvider.getExpiration();

        user = userRepository.findByUsername(username)
                .orElse(null);


        if(user == null){
            return ResponseDto.setFailed(ResponseMessage.NOT_EXISTS_USER);
        }

        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD);
        };

        Role role = user.getRole();

        String token = jwtProvider.generateJwtToken(username, role);

        data = new UserSignInResponseDto(token, user, exprTime);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }
}
