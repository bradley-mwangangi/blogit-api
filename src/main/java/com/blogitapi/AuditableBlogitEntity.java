package com.blogitapi;

import java.time.LocalDateTime;

public interface AuditableBlogitEntity {
    void setCreatedAt(LocalDateTime createdAtDate);
    LocalDateTime getCreatedAt();
    void setUpdatedAt(LocalDateTime updatedAtDate);
    LocalDateTime getUpdatedAt();
}
