package com.chubaievskyi.mapper;

import com.chubaievskyi.dto.UserDto;
import com.chubaievskyi.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", source = "id")
    UserDto entityToDto(UserEntity userEntity);

    UserEntity dtoToEntity(UserDto userDto);

}