package com.example.appointments.repository;

import com.example.appointments.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByCustomerId(String customerId);

    Optional<Appointment> findByIdAndCustomerId(Long id, String customerId);

    void deleteByCustomerId(String customerId);

    void deleteByIdAndCustomerId(Long id, String customerId);
}
