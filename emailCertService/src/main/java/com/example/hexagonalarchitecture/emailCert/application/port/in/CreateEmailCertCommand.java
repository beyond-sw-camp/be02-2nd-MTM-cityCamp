package com.example.hexagonalarchitecture.emailCert.application.port.in;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateEmailCertCommand {
    @NotNull
    private final String email;

    public CreateEmailCertCommand(String email) {
        this.email = email;
    }
}
