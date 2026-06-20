package com.adhavan.benshoppy.dto.user;

import com.adhavan.benshoppy.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateStatusRequest {


    @NotNull
    private Status status;

}
