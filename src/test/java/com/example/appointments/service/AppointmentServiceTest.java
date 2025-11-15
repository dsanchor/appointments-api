package com.example.appointments.service;

import com.example.appointments.dto.AppointmentRequest;
import com.example.appointments.dto.AppointmentResponse;
import com.example.appointments.exception.AppointmentNotFoundException;
import com.example.appointments.model.Appointment;
import com.example.appointments.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment appointment;
    private AppointmentRequest appointmentRequest;

    @BeforeEach
    void setUp() {
        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setTitle("Test Appointment");
        appointment.setNotes("Test Notes");
        appointment.setCategory("Medical");
        appointment.setStartDate(LocalDateTime.now());
        appointment.setDone(false);
        appointment.setCustomerId("123456789A");

        appointmentRequest = new AppointmentRequest();
        appointmentRequest.setTitle("Test Appointment");
        appointmentRequest.setNotes("Test Notes");
        appointmentRequest.setCategory("Medical");
        appointmentRequest.setStartDate(LocalDateTime.now());
        appointmentRequest.setDone(false);
        appointmentRequest.setCustomerId("123456789A");
    }

    @Test
    void testCreateAppointment() {
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        AppointmentResponse response = appointmentService.createAppointment(appointmentRequest);

        assertNotNull(response);
        assertEquals(appointment.getId(), response.getId());
        assertEquals(appointment.getTitle(), response.getTitle());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testGetAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(Arrays.asList(appointment));

        List<AppointmentResponse> responses = appointmentService.getAllAppointments();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void testGetAppointmentById() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        AppointmentResponse response = appointmentService.getAppointmentById(1L);

        assertNotNull(response);
        assertEquals(appointment.getId(), response.getId());
        verify(appointmentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAppointmentByIdNotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> {
            appointmentService.getAppointmentById(1L);
        });
    }

    @Test
    void testGetAppointmentsByCustomerId() {
        when(appointmentRepository.findByCustomerId("123456789A")).thenReturn(Arrays.asList(appointment));

        List<AppointmentResponse> responses = appointmentService.getAppointmentsByCustomerId("123456789A");

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(appointmentRepository, times(1)).findByCustomerId("123456789A");
    }

    @Test
    void testUpdateAppointment() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        AppointmentResponse response = appointmentService.updateAppointment(1L, appointmentRequest);

        assertNotNull(response);
        assertEquals(appointment.getId(), response.getId());
        verify(appointmentRepository, times(1)).findById(1L);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void testDeleteAppointment() {
        when(appointmentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(appointmentRepository).deleteById(1L);

        appointmentService.deleteAppointment(1L);

        verify(appointmentRepository, times(1)).existsById(1L);
        verify(appointmentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAppointmentNotFound() {
        when(appointmentRepository.existsById(1L)).thenReturn(false);

        assertThrows(AppointmentNotFoundException.class, () -> {
            appointmentService.deleteAppointment(1L);
        });
    }

    @Test
    void testDeleteAppointmentsByCustomerId() {
        doNothing().when(appointmentRepository).deleteByCustomerId("123456789A");

        appointmentService.deleteAppointmentsByCustomerId("123456789A");

        verify(appointmentRepository, times(1)).deleteByCustomerId("123456789A");
    }
}
