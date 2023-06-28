package az.tarlan.taskms.mapper;

import az.tarlan.taskms.dto.request.TaskCreationRequest;
import az.tarlan.taskms.dto.response.TaskResponse;
import az.tarlan.taskms.model.Task;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "title", target = "title")
    TaskResponse taskEntityToDto(Task task);

}
