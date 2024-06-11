package com.todo.common;

public interface Transformer<DTO> {
  void transform(DTO entity);
}
