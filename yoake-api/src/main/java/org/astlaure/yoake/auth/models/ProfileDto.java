package org.astlaure.yoake.auth.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileDto {
    private Long id;
    private String name;
    private String username;
    private String role;
}
