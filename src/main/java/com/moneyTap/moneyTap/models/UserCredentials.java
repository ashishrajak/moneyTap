package com.moneyTap.moneyTap.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCredentials {
    @NotNull(message = "enter username")
    @NotBlank(message = "enter usename")
    private String username;
    @NotNull(message = "enter password")
    @NotBlank(message = "enter pasword")
    private String password;
    @NotNull(message = "enter ApplicationName")
    @NotBlank(message = "enter ApplicationName")
    private String applicationName;
}
