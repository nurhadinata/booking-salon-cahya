package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ReservationRepository;
import com.booking.repositories.ServiceRepository;

public class ReservationService {
    private static Scanner input = new Scanner(System.in);
    List<Reservation> reservationsList = new ArrayList<>();

    public static void createReservation(){
        List<Person> listAllPersons = PersonRepository.getAllPerson();
        AtomicInteger num1=new AtomicInteger(0);
        System.out.printf("| %-4s | %-7s | %-11s | %-20s | %-15s | %-15s |\n",
                "No.", "ID", "Nama", "Alamat", "Membership", "Uang");
        System.out.println("+=========================================================================================+");
        listAllPersons.stream()
            .filter(person -> person instanceof Customer)
            .map(customer -> (Customer)customer)
            .forEach(customer ->{
                System.out.printf("| %-4s | %-7s | %-11s | %-20s | %-15s | Rp %,12.0f |\n",
                num1.incrementAndGet(), customer.getId(), customer.getName(), customer.getAddress(), customer.getMember().getMembershipName(), customer.getWallet());
            });
        
        boolean validasiCustomer = false;
        Customer choosedCustomer;
        do{
            System.out.println("Silahkan Masukkan Customer Id:");
            String inputCustomerID = input.nextLine();
            choosedCustomer = listAllPersons.stream()
                                .filter(person -> person instanceof Customer)
                                .map(customer -> (Customer)customer)
                                .filter(customer -> customer.getId().equals(inputCustomerID))
                                .findFirst()
                                .orElse(null);
            if(choosedCustomer != null){
                validasiCustomer = true;
            }else{
                System.out.println("Customer yang dicari tidak tersedia");
            }
        }while(!validasiCustomer);

        
        AtomicInteger num2=new AtomicInteger(0);
        System.out.printf("| %-4s | %-7s | %-11s | %-20s | %-15s |\n",
                "No.", "ID", "Nama", "Alamat", "Pengalaman");
        System.out.println("+========================================================================+");
        listAllPersons.stream()
            .filter(person -> person instanceof Employee)
            .map(employees -> (Employee)employees)
            .forEach(employee ->{
                System.out.printf("| %-4s | %-7s | %-11s | %-20s | %-15s |\n",
                num2.incrementAndGet(), employee.getId(), employee.getName(), employee.getAddress(), employee.getExperience());
            });
        boolean validasiEmployee = false;
        Employee choosedEmployee;
        do{
            System.out.println("Silahkan Masukkan Employee Id:");
            String inputEmployeeID = input.nextLine();
            
            choosedEmployee = listAllPersons.stream()
                                .filter(person -> person instanceof Employee)
                                .map(employee -> (Employee)employee)
                                .filter(employee -> employee.getId().equals(inputEmployeeID))
                                .findFirst()
                                .orElse(null);
            if(choosedEmployee != null){
                validasiEmployee = true;
            }else{
                System.out.println("Employee yang dicari tidak tersedia");
            }
        }while(!validasiEmployee);
        
        List<Service> listAllServices = ServiceRepository.getAllService();
        AtomicInteger num3=new AtomicInteger(0);
        System.out.printf("| %-4s | %-7s | %-20s | %-20s |\n",
                "No.", "ID", "Nama", "Harga");
        System.out.println("+==============================================================+");
        listAllServices.stream()
            .forEach(service ->{
                System.out.printf("| %-4s | %-7s | %-20s | Rp %,-17.0f |\n",
                num3.incrementAndGet(), service.getServiceId(), service.getServiceName(), service.getPrice());
            });

        List<Service> listChoosenServices = new ArrayList<Service>();
        boolean newReservation = true;
        do {
            System.out.println("Silahkan Masukkan Service Id:");
            String inputServiceID = input.nextLine();
            Service choosedService = listAllServices.stream()
                           .filter(service -> service.getServiceId().equals(inputServiceID))
                           .findFirst()
                           .orElse(null);
            Service confirmService = listChoosenServices.stream()
                            .filter(service -> service.getServiceId().equals(inputServiceID))
                            .findFirst()
                            .orElse(null);
            
            boolean inputValidation = false;
            if(choosedService!=null && confirmService == null){
                listChoosenServices.add(choosedService);
            }else if(choosedService == null){
                System.out.println("Service yang dicari tidak tersedia");
                inputValidation = true;
            }else{
                System.out.println("Service sudah dipilih");
            }
            
            
            while(!inputValidation){
                System.out.println("Ingin pilih service yang lain (Y/T)?");
                String continueInput = input.nextLine();
                if(continueInput.equalsIgnoreCase("T")){
                    newReservation = false;
                    inputValidation = true;
                }else if(continueInput.equalsIgnoreCase("Y")){
                    inputValidation = true;
                }else{
                    System.out.println("Input tidak valid!");
                    inputValidation = false;
                }
            };
        }while (newReservation);
        
        Reservation reservation = new Reservation(choosedCustomer, choosedEmployee, listChoosenServices, "In Process");
        ReservationRepository.AddReservation(reservation);

        
    }

    public static void getCustomerByCustomerId(){
        
    }

    public static void editReservationWorkstage(){
        List<Reservation> listAllReservations = ReservationRepository.getAllReservations();

        AtomicInteger num=new AtomicInteger(0);
        System.out.printf("| %-4s | %-7s | %-15s | %-35s | %-14s |\n",
                "No.", "ID", "Nama Customer", "Nama Service", "Total Biaya");
        System.out.println("+=========================================================================================+");
        listAllReservations.stream()
            .filter(reservation -> reservation.getWorkstage().equals("In Process"))
            .forEach(reservation ->{
                System.out.printf("| %-4s | %-7s | %-15s | %-35s | Rp %,12.0f|\n",
                num.incrementAndGet(), reservation.getReservationId(), reservation.getCustomer().getName(), reservation.getServicesList(), reservation.getReservationPrice());
            });
        
        boolean validateInput = false;
        Reservation choosedReservation;

        do{
            System.out.println("Silahkan Masukkan Reservation Id:");
            String inputReservationId = input.nextLine();

            choosedReservation = listAllReservations.stream()
                                .filter(reservation -> reservation.getReservationId().equals(inputReservationId))
                                .findFirst()
                                .orElse(null);
            if(choosedReservation != null){
                if (choosedReservation.getWorkstage().equals("In Process")){
                validateInput=true;
                }else{
                    System.out.println("Reservation yang dicari sudah selesai");
                    validateInput=false;
                }
            }else{
                System.out.println("Reservation yang dicari tidak tersedia");
                validateInput=false;
            }
        }while(!validateInput);

        boolean validateFinish= false;
        String inputFinish="";
        do{
            System.out.println("Selesaikan reservasi:");
            inputFinish = input.nextLine();
            if(inputFinish.equalsIgnoreCase("Finish")){
                choosedReservation.setWorkstage("Finish");
                validateFinish = true;
            }else if(inputFinish.equalsIgnoreCase("Cancel")){
                choosedReservation.setWorkstage("Canceled");
                validateFinish = true;
            }else{
                System.out.println("Input tidak valid!");
                validateFinish = false;
            }

        }while(!validateFinish);



    }

    // Silahkan tambahkan function lain, dan ubah function diatas sesuai kebutuhan
}
