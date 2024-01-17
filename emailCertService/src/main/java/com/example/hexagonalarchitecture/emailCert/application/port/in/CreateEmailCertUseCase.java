package com.example.hexagonalarchitecture.emailCert.application.port.in;

import com.example.hexagonalarchitecture.emailCert.domain.EmailCert;

public interface CreateEmailCertUseCase {
    EmailCert createEmailCert(CreateEmailCertCommand command);
}
