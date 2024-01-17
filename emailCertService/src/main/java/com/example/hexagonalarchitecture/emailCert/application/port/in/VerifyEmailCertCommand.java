package com.example.hexagonalarchitecture.emailCert.application.port.in;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class VerifyEmailCertCommand {
    @NotNull
    private final String email;

    @NotNull
    private final String uuid;
}
