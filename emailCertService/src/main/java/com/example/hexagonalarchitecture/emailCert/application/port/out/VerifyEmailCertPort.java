package com.example.hexagonalarchitecture.emailCert.application.port.out;

import com.example.hexagonalarchitecture.emailCert.domain.EmailCert;

public interface VerifyEmailCertPort {
    Boolean verifyEmailCert(EmailCert emailCert);
}
