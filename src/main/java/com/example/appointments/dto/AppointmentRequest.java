package com.example.appointments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String notes;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotNull(message = "Start date cannot be null")
    private LocalDateTime startDate;

    private Boolean done;

    @NotBlank(message = "Customer ID cannot be blank")
    private String customerId;
}
