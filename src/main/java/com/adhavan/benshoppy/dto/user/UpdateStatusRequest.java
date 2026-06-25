package com.adhavan.benshoppy.dto.user;

import com.adhavan.benshoppy.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusRequest {


    @NotNull
    private Status status;

}
