package com.example.hexagonalarchitecture.emailCert.adapter.out.persistence;

import com.example.hexagonalarchitecture.common.PersistenceAdapter;
import com.example.hexagonalarchitecture.emailCert.application.port.out.CreateEmailCertPort;
import com.example.hexagonalarchitecture.emailCert.application.port.out.VerifyEmailCertPort;
import com.example.hexagonalarchitecture.emailCert.domain.EmailCert;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class EmailCertPersistenceAdapter implements CreateEmailCertPort, VerifyEmailCertPort {

    private final SpringDataEmailCertRepository emailCertRepository;

    @Override
    public EmailCertJpaEntity createEmailCert(EmailCert emailCert) {
        return emailCertRepository.save(
                EmailCertJpaEntity.builder()
                        .email(emailCert.getEmail())
                        .uuid(emailCert.getUuid())
                        .build());
    }

    @Override
    public Boolean verifyEmailCert(EmailCert emailCert) {
        Optional<EmailCertJpaEntity> result = emailCertRepository.findByEmail(emailCert.getEmail());
        if (result.isPresent()) {
            EmailCertJpaEntity entity = result.get();
            if (emailCert.getEmail().equals(entity.getEmail()) &&
                    emailCert.getUuid().equals(entity.getUuid())) {
                return true;
            }
            return false;
        }
        return false;
    }
}
