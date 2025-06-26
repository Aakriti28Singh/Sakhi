package com.Backend.Login.DTO;

import lombok.Data;

@Data
public class ApiResponseDTO {

    private boolean success;
    private String message;
    private Object data;

    public ApiResponseDTO(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
