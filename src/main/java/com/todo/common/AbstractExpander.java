package com.todo.common;

import org.springframework.data.domain.Page;
import javax.annotation.PostConstruct;
import java.util.*;

public abstract class AbstractExpander<DTO> {
  // 定义存放transformer的map
  private Map<String, Transformer<DTO>> transformers = new HashMap<>();

  private List<String> populatedFieldsList = new ArrayList<>();

  // 初始化transformer
  @PostConstruct
  public void init() {
    initTransformers(transformers);
  }

  public AbstractExpander<DTO> setPopulatedFieldsList(String populatedFields) {
    populatedFieldsList = populatedFields != null && !populatedFields.isEmpty() ? Arrays.asList(populatedFields.split(",")) : null;
    return this;
  }

  // 为DTO填充字段
  public DTO expandFields(DTO dto) {
    if(dto == null) return null;
    // 为DTO填充字段
    if(!populatedFieldsList.isEmpty()) populatedFieldsList.stream().map(transformers::get).filter(Objects::nonNull).forEach(transformer -> transformer.transform(dto));

    return dto;
  }

  // 为Page<DTO>填充字段
  public Page<DTO> expandFields(Page<DTO> pageDTO) {
    return pageDTO != null ? pageDTO.map(this::expandFields) : null;
  }

  // 抽象的initTransformer、initPopulatedFields方法，由子类实现内部逻辑
  abstract public void initTransformers(Map<String, Transformer<DTO>> transformers);
}
