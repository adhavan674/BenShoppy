package com.adhavan.benshoppy.mapper;

import com.adhavan.benshoppy.dto.auth.SignupRequest;
import com.adhavan.benshoppy.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

   User SignupRequestToUser(SignupRequest dto);

}
