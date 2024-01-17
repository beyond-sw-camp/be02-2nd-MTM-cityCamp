package com.example.hexagonalarchitecture.emailCert.application.port.out;

import com.example.hexagonalarchitecture.emailCert.domain.EmailCert;

public interface SendEmailPort {
    void sendEmail(EmailCert emailCert);
}
