package com.genie.gymgenie.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnDec {

    public static String enDecEmail(String email, String operation) {
        byte[] bytes;
        if(operation.equals("encode")){
            bytes = email.getBytes(StandardCharsets.UTF_8);
            return Base64.getEncoder().encodeToString(bytes);
        } else {
            bytes = Base64.getDecoder().decode(email);
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }
}
