package com.chubaievskyi.mapper;

import com.chubaievskyi.dto.TaskDto;
import com.chubaievskyi.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper MAPPER = Mappers.getMapper(TaskMapper.class);

//    @Mapping(target = "id", source = "id")
    @Mapping(target = "owner", source = "owner", ignore = true)
    TaskDto entityToDto(TaskEntity taskEntity);

    TaskEntity dtoToEntity(TaskDto taskDto);

}