package com.diep.CreateAPI_BE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CollectionDto {
    private Long id;
    private String name;
    private Boolean isShared;
    private String apiKey;
}
