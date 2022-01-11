package com.works.metrostation.dto;

import com.works.metrostation.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotEmpty
    @Size(min = 2, max = 64)
    private String username;

    @NotEmpty
    @Size(min = 6, max = 32)
    private String password;

    @NotNull
    private UserRole role;
}
