package com.example.shareIt.exception;

import lombok.Value;

@Value
public class ErrorResponse {

    String error;
    String description;

}
