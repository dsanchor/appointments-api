package com.example.appointments.service;

import com.example.appointments.dto.AppointmentRequest;
import com.example.appointments.dto.AppointmentResponse;
import com.example.appointments.exception.AppointmentNotFoundException;
import com.example.appointments.model.Appointment;
import com.example.appointments.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private static final Logger log = LoggerFactory.getLogger(AppointmentService.class);
    private final AppointmentRepository appointmentRepository;

    public AppointmentResponse createAppointment(AppointmentRequest request) {
        log.info("Creating appointment for customer {}", request.getCustomerId());
        
        Appointment appointment = new Appointment();
        appointment.setTitle(request.getTitle());
        appointment.setNotes(request.getNotes());
        appointment.setCategory(request.getCategory());
        appointment.setStartDate(request.getStartDate());
        appointment.setDone(request.getDone() != null ? request.getDone() : false);
        appointment.setCustomerId(request.getCustomerId());

        Appointment saved = appointmentRepository.save(appointment);
        log.info("Created appointment with ID {}", saved.getId());
        
        return AppointmentResponse.fromEntity(saved);
    }

    public List<AppointmentResponse> getAllAppointments() {
        log.debug("Retrieving all appointments");
        return appointmentRepository.findAll().stream()
            .map(AppointmentResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public AppointmentResponse getAppointmentById(Long id) {
        log.debug("Retrieving appointment with ID {}", id);
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + id));
        return AppointmentResponse.fromEntity(appointment);
    }

    public List<AppointmentResponse> getAppointmentsByCustomerId(Long customerId) {
        log.info("Retrieving appointments for customer {}", customerId);
        return appointmentRepository.findByCustomerId(customerId).stream()
            .map(AppointmentResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        log.info("Updating appointment with ID {}", id);
        
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + id));

        appointment.setTitle(request.getTitle());
        appointment.setNotes(request.getNotes());
        appointment.setCategory(request.getCategory());
        appointment.setStartDate(request.getStartDate());
        appointment.setDone(request.getDone() != null ? request.getDone() : false);
        appointment.setCustomerId(request.getCustomerId());

        Appointment updated = appointmentRepository.save(appointment);
        log.info("Updated appointment with ID {}", updated.getId());
        
        return AppointmentResponse.fromEntity(updated);
    }

    public AppointmentResponse updateAppointmentByCustomer(Long customerId, Long appointmentId, AppointmentRequest request) {
        log.info("Updating appointment {} for customer {}", appointmentId, customerId);
        
        Appointment appointment = appointmentRepository.findByIdAndCustomerId(appointmentId, customerId)
            .orElseThrow(() -> new AppointmentNotFoundException(
                "Appointment not found with id: " + appointmentId + " for customer: " + customerId));

        appointment.setTitle(request.getTitle());
        appointment.setNotes(request.getNotes());
        appointment.setCategory(request.getCategory());
        appointment.setStartDate(request.getStartDate());
        appointment.setDone(request.getDone() != null ? request.getDone() : false);

        Appointment updated = appointmentRepository.save(appointment);
        log.info("Updated appointment {} for customer {}", updated.getId(), customerId);
        
        return AppointmentResponse.fromEntity(updated);
    }

    @Transactional
    public void deleteAppointment(Long id) {
        log.info("Deleting appointment with ID {}", id);
        if (!appointmentRepository.existsById(id)) {
            throw new AppointmentNotFoundException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
        log.info("Deleted appointment with ID {}", id);
    }

    @Transactional
    public void deleteAppointmentsByCustomerId(Long customerId) {
        log.info("Deleting all appointments for customer {}", customerId);
        appointmentRepository.deleteByCustomerId(customerId);
        log.info("Deleted all appointments for customer {}", customerId);
    }

    @Transactional
    public void deleteAppointmentByCustomer(Long customerId, Long appointmentId) {
        log.info("Deleting appointment {} for customer {}", appointmentId, customerId);
        
        if (!appointmentRepository.findByIdAndCustomerId(appointmentId, customerId).isPresent()) {
            throw new AppointmentNotFoundException(
                "Appointment not found with id: " + appointmentId + " for customer: " + customerId);
        }
        
        appointmentRepository.deleteByIdAndCustomerId(appointmentId, customerId);
        log.info("Deleted appointment {} for customer {}", appointmentId, customerId);
    }
}
