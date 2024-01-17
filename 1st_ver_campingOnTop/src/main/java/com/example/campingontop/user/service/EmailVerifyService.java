package com.example.campingontop.user.service;

import com.example.campingontop.user.model.EmailVerify;
import com.example.campingontop.user.model.request.PostEmailConfirmDtoReq;
import com.example.campingontop.user.repository.EmailVerifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {
    private final EmailVerifyRepository emailVerifyRepository;

    public void create(String email, String token) {
        EmailVerify emailVerify = EmailVerify.builder()
                .email(email)
                .token(token)
                .build();
        emailVerifyRepository.save(emailVerify);
    }

    public Boolean verify(PostEmailConfirmDtoReq req) {
        Optional<EmailVerify> result = emailVerifyRepository.findByEmail(req.getEmail());
        if(result.isPresent()){
            EmailVerify emailVerify = result.get();
            if(emailVerify.getToken().equals(req.getToken())) {
                return true;
            }
        }
        return false;
    }
}
