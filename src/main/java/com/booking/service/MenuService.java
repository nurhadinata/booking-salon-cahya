package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ReservationRepository;
import com.booking.repositories.ServiceRepository;

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    private static List<Reservation> reservationList = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);

    public static void mainMenu() {
        String[] mainMenuArr = {"Show Data", "Create Reservation", "Complete/cancel reservation", "Exit"};
        String[] subMenuArr = {"Recent Reservation", "Show Customer", "Show Available Employee", "Reservation History", "Back to main menu"};
    
        int optionMainMenu;
        int optionSubMenu;

		boolean backToMainMenu = true;
        boolean backToSubMenu = true;
        
        do {
            PrintService.printMenu("Main Menu", mainMenuArr);
            optionMainMenu = Integer.valueOf(input.nextLine());
            switch (optionMainMenu) {
                case 1:
                    backToSubMenu = true;
                    do {
                        PrintService.printMenu("Show Data", subMenuArr);
                        optionSubMenu = Integer.valueOf(input.nextLine());
                        // Sub menu - menu 1
                        switch (optionSubMenu) {
                            case 1:
                                // panggil fitur tampilkan recent reservation
                                new PrintService().showRecentReservation();
                                break;
                            case 2:
                                // panggil fitur tampilkan semua customer
                                new PrintService().showAllCustomer();
                                break;
                            case 3:
                                // panggil fitur tampilkan semua employee
                                new PrintService().showAllEmployee();
                                break;
                            case 4:
                                // panggil fitur tampilkan history reservation + total keuntungan
                                new PrintService().showHistoryReservation();
                                break;
                            case 0:
                                backToSubMenu = false;
                                break;
                            default:
                                System.out.println("Input tidak valid");
                        }
                    } while (backToSubMenu);
                    break;
                case 2:
                    // panggil fitur menambahkan reservation
                    ReservationService.createReservation();
                    break;
                case 3:
                    // panggil fitur mengubah workstage menjadi finish/cancel
                    ReservationService.editReservationWorkstage();
                    break;
                case 0:
                    backToMainMenu = false;
                    break;
                default:
                    System.out.println("Input tidak valid");
            }
        } while (backToMainMenu);
		
	}
}
