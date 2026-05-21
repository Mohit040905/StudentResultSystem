package com.studentresult.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Standard API response wrapper.
 * Every endpoint returns this structure so the response is consistent.
 *
 * Example response:
 * {
 *   "success": true,
 *   "message": "Student added successfully",
 *   "data": { ... },
 *   "timestamp": "2026-01-15T10:30:00"
 * }
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // Quick factory methods
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
