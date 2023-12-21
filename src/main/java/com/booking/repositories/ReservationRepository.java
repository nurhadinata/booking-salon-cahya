package com.booking.repositories;

import java.util.ArrayList;
import java.util.List;

import com.booking.models.Reservation;

public class ReservationRepository {
    static List<Reservation> listReservations = new ArrayList<Reservation>();
    int existingID = 0;
    public static void AddReservation(Reservation reservation){
        listReservations.add(reservation);
    }

    public static List<Reservation> getAllReservations(){
        return listReservations;
    }
}
