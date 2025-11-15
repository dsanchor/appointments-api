package com.example.appointments.controller;

import com.example.appointments.dto.AppointmentRequest;
import com.example.appointments.dto.AppointmentResponse;
import com.example.appointments.service.AppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private AppointmentRequest appointmentRequest;
    private AppointmentResponse appointmentResponse;

    @BeforeEach
    void setUp() {
        LocalDateTime startDate = LocalDateTime.of(2025, 11, 15, 10, 0);

        appointmentRequest = new AppointmentRequest();
        appointmentRequest.setTitle("Test Appointment");
        appointmentRequest.setNotes("Test Notes");
        appointmentRequest.setCategory("Medical");
        appointmentRequest.setStartDate(startDate);
        appointmentRequest.setDone(false);
        appointmentRequest.setCustomerId("123456789A");

        appointmentResponse = new AppointmentResponse();
        appointmentResponse.setId(1L);
        appointmentResponse.setTitle("Test Appointment");
        appointmentResponse.setNotes("Test Notes");
        appointmentResponse.setCategory("Medical");
        appointmentResponse.setStartDate(startDate);
        appointmentResponse.setDone(false);
        appointmentResponse.setCustomerId("123456789A");
    }

    @Test
    void testCreateAppointment() throws Exception {
        when(appointmentService.createAppointment(any(AppointmentRequest.class)))
                .thenReturn(appointmentResponse);

        mockMvc.perform(post("/api/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appointmentRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Appointment"))
                .andExpect(jsonPath("$.customerId").value("123456789A"));
    }

    @Test
    void testGetAllAppointments() throws Exception {
        List<AppointmentResponse> responses = Arrays.asList(appointmentResponse);
        when(appointmentService.getAllAppointments()).thenReturn(responses);

        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Appointment"));
    }

    @Test
    void testGetAppointmentById() throws Exception {
        when(appointmentService.getAppointmentById(1L)).thenReturn(appointmentResponse);

        mockMvc.perform(get("/api/appointments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Appointment"));
    }

    @Test
    void testGetAppointmentsByCustomerId() throws Exception {
        List<AppointmentResponse> responses = Arrays.asList(appointmentResponse);
        when(appointmentService.getAppointmentsByCustomerId("123456789A")).thenReturn(responses);

        mockMvc.perform(get("/api/appointments/customer/123456789A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].customerId").value("123456789A"));
    }

    @Test
    void testUpdateAppointment() throws Exception {
        when(appointmentService.updateAppointment(eq(1L), any(AppointmentRequest.class)))
                .thenReturn(appointmentResponse);

        mockMvc.perform(put("/api/appointments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appointmentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Appointment"));
    }

    @Test
    void testDeleteAppointment() throws Exception {
        mockMvc.perform(delete("/api/appointments/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteAppointmentsByCustomerId() throws Exception {
        mockMvc.perform(delete("/api/appointments/customer/123456789A"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateAppointmentWithValidationError() throws Exception {
        AppointmentRequest invalidRequest = new AppointmentRequest();
        invalidRequest.setTitle(""); // Invalid - blank title
        invalidRequest.setCustomerId("123456789A");

        mockMvc.perform(post("/api/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
