package com.spring.andorinha.backend.service;

import com.spring.andorinha.backend.config.security.JwtService;
import com.spring.andorinha.backend.entity.UserEntity;
import com.spring.andorinha.backend.record.UserRecord;
import com.spring.andorinha.backend.record.request.AuthenticationRequestRecord;
import com.spring.andorinha.backend.record.response.AuthenticationResponseRecord;
import com.spring.andorinha.backend.record.request.RegisterRequestRecord;
import com.spring.andorinha.backend.repository.UserRepository;
import com.spring.andorinha.backend.utils.enums.Role;
import com.spring.andorinha.backend.utils.helper.ConverterHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponseRecord register(RegisterRequestRecord request) {

        var user = new UserRecord(
                null,
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.USER
        );

        UserEntity userEntity = userRepository.save(ConverterHelper.convertRecordToEntity(user, UserEntity.class));

        var jwtToken = jwtService.generateToken(userEntity);
        return new AuthenticationResponseRecord (
                jwtToken
        );
    }

    public AuthenticationResponseRecord authenticate(AuthenticationRequestRecord request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var userEntity = userRepository.findByEmail(request.email())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(userEntity);
        return new AuthenticationResponseRecord (
                jwtToken
        );
    }
}
