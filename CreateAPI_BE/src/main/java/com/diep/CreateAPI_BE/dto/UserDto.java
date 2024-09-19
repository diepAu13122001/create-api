package com.diep.CreateAPI_BE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String email;
    private String name;
    private String apiKey;
    private String provider;
}
