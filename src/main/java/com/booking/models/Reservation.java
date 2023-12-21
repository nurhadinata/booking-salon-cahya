package com.booking.models;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private static final AtomicInteger count = new AtomicInteger(1);
    private String reservationId;
    private Customer customer;
    private Employee employee;
    private List<Service> services;
    private double reservationPrice;
    private String workstage;
    //   workStage (In Process, Finish, Canceled)

    public Reservation(Customer customer, Employee employee, List<Service> services,
            String workstage) {
        String id = "";
        if(count.get()<10){
            id = "Rsv-0"+count.getAndIncrement();
        }else{
            id = "Rsv-"+count.getAndIncrement();
        }
        this.reservationId = id;
        this.customer = customer;
        this.employee = employee;
        this.services = services;
        this.reservationPrice = calculateReservationPrice();
        this.workstage = workstage;
    };

    private double calculateReservationPrice(){
        double reservationPrice = 0;
        for (Service service : this.services) {
            reservationPrice += service.getPrice();
        }

        if(this.customer.getMember().getMembershipName().equalsIgnoreCase("silver")){
            reservationPrice = reservationPrice*95/100;
        }else if(this.customer.getMember().getMembershipName().equalsIgnoreCase("gold")){
            reservationPrice = reservationPrice*90/100;
        }
        
        return reservationPrice;
    }

    public String getServicesList(){
        String servicesList = "";
        for (Service service : this.services) {
            if(servicesList.equals("")){
                servicesList = service.getServiceName();
            }else{
                servicesList = servicesList +", "+ service.getServiceName();
            }
            
        }
        return servicesList;
    }
}
