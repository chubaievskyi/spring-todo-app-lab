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
//    @Mapping(target = "createdAt", source = "createdAt", ignore = true)
//    @Mapping(target = "createdBy", source = "createdBy", ignore = true)
    TaskDto entityToDto(TaskEntity taskEntity);

//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "createdBy", ignore = true)
    TaskEntity dtoToEntity(TaskDto taskDto);

}