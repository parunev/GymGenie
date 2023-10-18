package com.genie.gymgenie.mapper;

import com.genie.gymgenie.models.User;
import com.genie.gymgenie.models.payload.user.registration.RegistrationRequest;
import com.genie.gymgenie.utils.mapping.EncodedMapping;
import com.genie.gymgenie.utils.mapping.PasswordEncoderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface AuthMapper {

    @Mapping(target = "authority", expression = "java(com.genie.gymgenie.models.enums.Authority.AUTHORITY_USER)")
    @Mapping(target = "password", source = "request.password", qualifiedBy = EncodedMapping.class)
    User requestToUser(RegistrationRequest request);
}
