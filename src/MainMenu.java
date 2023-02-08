import api.HotelResource;
import model.*;
import service.CustomerService;
import service.ReservationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import static java.lang.System.exit;


public class MainMenu {
    static final HotelResource hotelResource = HotelResource.getInstance();

    public static void menuOptions() {
        //Session Lead recommended use of Boolean in helper video, applied here:
        boolean keepRunning = true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (keepRunning) {
                int selection = 0;
                try {
                    System.out.println("1. Find and Reserve a Room");
                    System.out.println("2. See my reservations");
                    System.out.println("3. Create an account");
                    System.out.println("4. Admin");
                    System.out.println("5. Exit");
                    System.out.println("Please select 1-5 from the options provided.");
                    selection = Integer.parseInt(scanner.nextLine());
                    switch (selection) {
                        case 1:
                            findAndReserveARoom();
                            break;
                        case 2:
                            seeMyReservations();
                            break;
                        case 3:
                            createAnAccount();
                            break;
                        case 4:
                            AdminMenu.printOptions();
                            break;
                        case 5:
                            System.out.println("Exiting application...");
                            exit(0);
                            break;
                        default:
                            System.out.println("Invalid Entry: Please select 1-5 from the menu options.");
                    }
                } catch (Exception ex) {
                    System.out.println("Error Message: Invalid Input");
                }
            }
        }
    }

    private static void findAndReserveARoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you have an account already? Enter y or n");
        String hasAccount = scanner.next().toLowerCase();
        if(hasAccount.equals("y")){
            System.out.println("Please enter your user associated email to find and book room.");
            String email = scanner.next();
            if(hotelResource.getCustomer(email) == null){
                System.out.println("Please create an account before booking a room!");
                return;
            }
            System.out.println("Select room type: 1 for Single OR 2 for Double");
            String roomType = scanner.next();
            RoomType roomTypeSelection = RoomType.getRoomType(Integer.parseInt(roomType));
            System.out.println("Enter a check in date: MM/DD/YYYY");
            String checkIn = scanner.next();
            DateFormat validDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date checkInDate;
            try{
                checkInDate = validDateFormat.parse(checkIn);
            } catch (ParseException e) {
                System.out.println("Invalid Date Format! USE MM/DD/YYYY");
                return;
            }
            System.out.println("Enter a check out date: MM/DD/YYYY");
            String checkOut = scanner.next();
            Date checkOutDate;
            try{
                checkOutDate = validDateFormat.parse(checkOut);
            } catch (ParseException e) {
                System.out.println("Invalid Date Format! USE MM/DD/YYYY");
                return;
            }
            Collection<IRoom> potentialRooms = hotelResource.findARoom(checkInDate, checkOutDate);
            Collection<IRoom> filteredRooms = new ArrayList<>();
            for(IRoom room : potentialRooms) {
                if(room.getRoomType().equals(roomTypeSelection)){
                    filteredRooms.add(room);
                }
            }
            for(IRoom room : filteredRooms) {
                System.out.println(room.toString());
            }
            if(filteredRooms.isEmpty()){
                System.out.println("There are no available rooms for your specified dates. Looking for other options...");
                Collection<IRoom> altRooms = hotelResource.findAlternativeRooms(checkInDate, checkOutDate);
                if(altRooms.isEmpty()){
                    System.out.println("NO VACANCY.");
                } else {
                    System.out.println("Available rooms for alternative dates:");
                    LocalDate checkInPlus7 = LocalDate.ofInstant(checkInDate.toInstant(), ZoneId.systemDefault()).plusDays(7);
                    Date checkInPlus7Date = Date.from(checkInPlus7.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    LocalDate checkOutPlus7 = LocalDate.ofInstant(checkOutDate.toInstant(), ZoneId.systemDefault()).plusDays(7);
                    Date checkOutPlus7Date = Date.from(checkOutPlus7.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    System.out.println("Alternate Check-in: " + checkInPlus7Date);
                    System.out.println("Alternate Check-out: " + checkOutPlus7Date);
                    for(IRoom room : altRooms) {
                        System.out.println(room.toString());
                    }
                    System.out.println("Type an available room number.");
                    Integer roomNumberSelection = scanner.nextInt();
                    IRoom roomInput = hotelResource.getRoom(roomNumberSelection);
                    if(roomInput == null){
                        System.out.println("Invalid input entered.");
                        return;
                    }
                    Reservation customerReservation = hotelResource.bookARoom(email, roomInput, checkInPlus7Date, checkOutPlus7Date);
                    if(customerReservation != null){
                        System.out.println("Your reservation has been successfully booked!");
                        System.out.println(customerReservation.toString());
                    }
                }
            }else{
                System.out.println("Type an available room number.");
                Integer roomNumberSelection = scanner.nextInt();
                IRoom roomInput = hotelResource.getRoom(roomNumberSelection);
                if(roomInput == null){
                    System.out.println("Invalid input entered.");
                    return;
                }
                Reservation customerReservation = hotelResource.bookARoom(email, roomInput, checkInDate, checkOutDate);
                if(customerReservation != null){
                    System.out.println("Your reservation has been successfully booked!");
                    System.out.println(customerReservation.toString());
                }
            }
        } else if (hasAccount.equals("n")){
            System.out.println("Please create an account before booking a room!");
        } else {
            System.out.println("Invalid input: Please type y for yes or n for no.");
        }
    }

    private static void createAnAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type First Name:");
        String firstName = scanner.next();
        System.out.println("Type Last Name:");
        String lastName = scanner.next();
        System.out.println("Type Email:");
        String email = scanner.next();
        hotelResource.createACustomer(firstName, lastName, email);
        System.out.println("Your account has been created!");
    }
    static final CustomerService customerService = CustomerService.getInstance();
    static final ReservationService reservationService = ReservationService.getInstance();
    private static void seeMyReservations(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter an email to find your reservations: ");
        String email = scanner.next();
        Customer customer = customerService.getCustomer(email);
        if(customer == null){
            System.out.println("No Account Exists for the email provided.");
            return;
        }
        Collection<Reservation> customerReservations = hotelResource.getCustomersReservations(email);
        if(customerReservations == null){
            System.out.println("No reservations have been found that match the email provided.");
            return;
        }
        for(Reservation reservation : customerReservations){
            System.out.println(reservation.toString());
        }
    }
}


