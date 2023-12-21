package com.booking.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.booking.models.*;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ReservationRepository;

public class PrintService {
    public static void printMenu(String title, String[] menuArr){
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {   
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);   
            num++;
        }
    }

    public String printServices(List<Service> serviceList){
        String result = "";
        // Bisa disesuaikan kembali
        for (Service service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public void showRecentReservation(){
        List<Reservation> listAllReservations = ReservationRepository.getAllReservations();

        AtomicInteger num=new AtomicInteger(0);
        double totalProfit =0;
        System.out.printf("| %-4s | %-7s | %-15s | %-25s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Nama Service", "Total Biaya", "Workstage");
        System.out.println("+=========================================================================================+");
        listAllReservations.stream()
            .filter(reservation -> reservation.getWorkstage().equals("In Process"))
            .forEach(reservation ->{
                System.out.printf("| %-4s | %-7s | %-15s | %-25s | Rp %,12.0f | %-10s |\n",
                num.incrementAndGet(), reservation.getReservationId(), reservation.getCustomer().getName(), reservation.getServicesList(), reservation.getReservationPrice(), reservation.getWorkstage());
            });
    }

    public void showAllCustomer(){
        List<Person> listAllPersons = PersonRepository.getAllPerson();

        AtomicInteger num=new AtomicInteger(0);
        System.out.printf("| %-4s | %-7s | %-11s | %-20s | %-15s | %-15s |\n",
                "No.", "ID", "Nama", "Alamat", "Membership", "Uang");
        System.out.println("+=========================================================================================+");
        listAllPersons.stream()
            .filter(person -> person instanceof Customer)
            .map(customer -> (Customer)customer)
            .forEach(customer ->{
                System.out.printf("| %-4s | %-7s | %-11s | %-20s | %-15s | Rp %,12.0f |\n",
                num.incrementAndGet(), customer.getId(), customer.getName(), customer.getAddress(), customer.getMember().getMembershipName(), customer.getWallet());
            });


    }

    public void showAllEmployee(){
        List<Person> listAllPersons = PersonRepository.getAllPerson();

        AtomicInteger num2=new AtomicInteger(0);
        System.out.printf("| %-4s | %-7s | %-11s | %-20s | %-15s |\n",
                "No.", "ID", "Nama", "Alamat", "Pengalaman");
        System.out.println("+=========================================================================================+");
        listAllPersons.stream()
            .filter(person -> person instanceof Employee)
            .map(employees -> (Employee)employees)
            .forEach(employee ->{
                System.out.printf("| %-4s | %-7s | %-11s | %-20s | %-15s |\n",
                num2.incrementAndGet(), employee.getId(), employee.getName(), employee.getAddress(), employee.getExperience());
            });
        
    }

    public void showHistoryReservation(){
        List<Reservation> listAllReservations = ReservationRepository.getAllReservations();

        AtomicInteger num=new AtomicInteger(0);
        double totalProfit =0;
        System.out.printf("| %-4s | %-7s | %-15s | %-20s | %-15s | %-15s |\n",
                "No.", "ID", "Nama Customer", "Service", "Total Biaya", "Workstage");
        System.out.println("+=============================================================================================+");
        listAllReservations.stream()
            .filter(reservation -> reservation.getWorkstage().equals("Finish")||reservation.getWorkstage().equals("Canceled"))
            .forEach(reservation ->{
                System.out.printf("| %-4s | %-7s | %-15s | %-20s | Rp %,12.0f | %-15s |\n",
                num.incrementAndGet(), reservation.getReservationId(), reservation.getCustomer().getName(), reservation.getServicesList(), reservation.getReservationPrice(), reservation.getWorkstage());
            });
        
        for (Reservation reservation : listAllReservations) {
            if(reservation.getWorkstage().equals("Finish")){
                // System.out.printf("| %-4s | %-7s | %-15s | %-20s | %-15s | Rp %,12.0f|\n",
                // num.incrementAndGet(), reservation.getReservationId(), reservation.getCustomer().getName(), reservation.getServicesList(), reservation.getReservationPrice());
                totalProfit += reservation.getReservationPrice();
            }
        }
        System.out.printf("| %-55s | Rp. %,29.0f |\n",
                "Total Keuntungan", totalProfit);
            
        
    }
}
