package com.example.hexagonalarchitecture.emailCert.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailCert {
    private final Long id;
    private final String email;
    private final String uuid;
}
