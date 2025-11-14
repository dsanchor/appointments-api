package com.example.appointments.dto;

import com.example.appointments.model.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {

    private Long id;
    private String title;
    private String notes;
    private String category;
    private LocalDateTime startDate;
    private Boolean done;
    private Long customerId;

    public static AppointmentResponse fromEntity(Appointment appointment) {
        return new AppointmentResponse(
            appointment.getId(),
            appointment.getTitle(),
            appointment.getNotes(),
            appointment.getCategory(),
            appointment.getStartDate(),
            appointment.getDone(),
            appointment.getCustomerId()
        );
    }
}
