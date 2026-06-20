package com.adhavan.benshoppy.mapper;

import com.adhavan.benshoppy.dto.user.DetailsUserOrSellerResponse;
import com.adhavan.benshoppy.dto.user.SummaryUserOrSellerResponse;
import com.adhavan.benshoppy.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {


    SummaryUserOrSellerResponse UserToSummary(User user);
    DetailsUserOrSellerResponse UserToDetails(User user);

}
