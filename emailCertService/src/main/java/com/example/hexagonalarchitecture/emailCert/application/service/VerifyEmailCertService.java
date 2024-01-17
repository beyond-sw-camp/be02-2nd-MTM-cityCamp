package com.example.hexagonalarchitecture.emailCert.application.service;

import com.example.hexagonalarchitecture.common.UseCase;
import com.example.hexagonalarchitecture.emailCert.application.port.in.VerifyEmailCertCommand;
import com.example.hexagonalarchitecture.emailCert.application.port.in.VerifyEmailCertUseCase;
import com.example.hexagonalarchitecture.emailCert.application.port.out.VerifyEmailCertPort;
import com.example.hexagonalarchitecture.emailCert.domain.EmailCert;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class VerifyEmailCertService implements VerifyEmailCertUseCase {
    private final VerifyEmailCertPort verifyEmailCertPort;


    @Override
    public Boolean verifyEmailCert(VerifyEmailCertCommand command) {
        EmailCert emailCert = EmailCert.builder()
                .email(command.getEmail())
                .uuid(command.getUuid())
                .build();

        Boolean response = verifyEmailCertPort.verifyEmailCert(emailCert);

        return response;
    }
}
