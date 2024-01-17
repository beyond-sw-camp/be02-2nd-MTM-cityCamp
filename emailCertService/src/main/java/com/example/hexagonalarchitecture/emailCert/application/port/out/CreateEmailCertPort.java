package com.example.hexagonalarchitecture.emailCert.application.port.out;

import com.example.hexagonalarchitecture.emailCert.adapter.out.persistence.EmailCertJpaEntity;
import com.example.hexagonalarchitecture.emailCert.domain.EmailCert;

public interface CreateEmailCertPort {
    EmailCertJpaEntity createEmailCert(EmailCert emailCert);
}
