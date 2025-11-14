package com.example.appointments.repository;

import com.example.appointments.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    List<Appointment> findByCustomerId(Long customerId);
    
    Optional<Appointment> findByIdAndCustomerId(Long id, Long customerId);
    
    void deleteByCustomerId(Long customerId);
    
    void deleteByIdAndCustomerId(Long id, Long customerId);
}
