package com.todo.dto;

import lombok.Data;

@Data
public abstract class CommonDTO {
    // ID
    private String id;
    // 创建时间
    private Long createdAt;
    // 更新时间
    private Long updatedAt;
}