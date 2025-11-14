package com.example.appointments.controller;

import com.example.appointments.dto.AppointmentRequest;
import com.example.appointments.dto.AppointmentResponse;
import com.example.appointments.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest request) {
        log.info("Received request to create appointment for customer {}", request.getCustomerId());
        AppointmentResponse response = appointmentService.createAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        log.info("Received request to get all appointments");
        List<AppointmentResponse> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id) {
        log.info("Received request to get appointment with ID {}", id);
        AppointmentResponse response = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByCustomerId(@PathVariable Long customerId) {
        log.info("Received request to get appointments for customer {}", customerId);
        List<AppointmentResponse> appointments = appointmentService.getAppointmentsByCustomerId(customerId);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequest request) {
        log.info("Received request to update appointment with ID {}", id);
        AppointmentResponse response = appointmentService.updateAppointment(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/customer/{customerId}/appointment/{appointmentId}")
    public ResponseEntity<AppointmentResponse> updateAppointmentByCustomer(
            @PathVariable Long customerId,
            @PathVariable Long appointmentId,
            @Valid @RequestBody AppointmentRequest request) {
        log.info("Received request to update appointment {} for customer {}", appointmentId, customerId);
        AppointmentResponse response = appointmentService.updateAppointmentByCustomer(customerId, appointmentId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        log.info("Received request to delete appointment with ID {}", id);
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Void> deleteAppointmentsByCustomerId(@PathVariable Long customerId) {
        log.info("Received request to delete all appointments for customer {}", customerId);
        appointmentService.deleteAppointmentsByCustomerId(customerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customer/{customerId}/appointment/{appointmentId}")
    public ResponseEntity<Void> deleteAppointmentByCustomer(
            @PathVariable Long customerId,
            @PathVariable Long appointmentId) {
        log.info("Received request to delete appointment {} for customer {}", appointmentId, customerId);
        appointmentService.deleteAppointmentByCustomer(customerId, appointmentId);
        return ResponseEntity.noContent().build();
    }
}
