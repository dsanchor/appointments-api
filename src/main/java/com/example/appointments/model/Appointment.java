package com.example.appointments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private Boolean done = false;

    @Column(nullable = false)
    private Long customerId;
}
