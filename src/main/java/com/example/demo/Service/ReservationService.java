package com.example.demo.Service;

import com.example.demo.DAO.ReservationRepository;
import com.example.demo.Entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    public Reservation getReservationById(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        return optionalReservation.orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setName(reservationDetails.getName());
            reservation.setPhone(reservationDetails.getPhone());
            reservation.setDatetime(reservationDetails.getDatetime());
            reservation.setNumberOfPeople(reservationDetails.getNumberOfPeople());
            reservation.setMessage(reservationDetails.getMessage());
            return reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}