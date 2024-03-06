package com.chubaievskyi.mapper;

import com.chubaievskyi.dto.TaskDto;
import com.chubaievskyi.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper MAPPER = Mappers.getMapper(TaskMapper.class);

    TaskDto entityToDto(TaskEntity taskEntity);
    TaskEntity dtoToEntity(TaskDto taskDto);

}