package com.authentication.auth_service.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String name;
    private String email;
    private AddressDto address;
}
