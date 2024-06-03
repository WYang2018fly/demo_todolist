package com.todo.util.mapper;
import com.todo.dto.task.TaskDTO;
import com.todo.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
  TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);
  TaskDTO taskEntityToTaskDTO(Task task);

  Task taskDTOToTaskEntity(TaskDTO taskDTO);
}
