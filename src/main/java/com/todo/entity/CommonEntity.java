package com.todo.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class CommonEntity {
    // ID
    @Id
    private String id;
    // 创建时间
    private Long createdAt;
    // 更新时间
    private Long updatedAt;
}
