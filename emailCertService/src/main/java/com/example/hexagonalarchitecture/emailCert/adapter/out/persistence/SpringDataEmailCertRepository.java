package com.example.hexagonalarchitecture.emailCert.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataEmailCertRepository extends JpaRepository<EmailCertJpaEntity, Long> {
    Optional<EmailCertJpaEntity> findByEmail(String email);
}
