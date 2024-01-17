package com.example.hexagonalarchitecture.emailCert.adapter.in.kafka;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class CreateEmailCertRequest {
    private Map<String, String> record;

}
