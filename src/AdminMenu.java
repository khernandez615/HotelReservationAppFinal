import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Collection;
import java.util.Scanner;

public class AdminMenu {
    static final AdminResource adminResource = AdminResource.getInstance();

    public static void printOptions() {
        boolean keepRunning = true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (keepRunning) {
                int selection = 0;
                try {
                    System.out.println("1. See all customers");
                    System.out.println("2. See all rooms");
                    System.out.println("3. See all reservations");
                    System.out.println("4. Add a room");
                    System.out.println("5. Back to main menu");
                    System.out.println("Please select 1-5 from the options provided.");
                    selection = Integer.parseInt(scanner.nextLine());
                    switch (selection) {
                        case 1:
                            printAllCustomers();
                            break;
                        case 2:
                            printAllRooms();
                            break;
                        case 3:
                            adminResource.displayAllReservations();
                            break;
                        case 4:
                            addARoom();
                            break;
                        case 5:
                            System.out.println("Returning to main menu...");
                            MainMenu.menuOptions();
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
    private static void addARoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a room number:");
        int roomNumber = scanner.nextInt();
        Collection<IRoom> allRooms = adminResource.getAllRooms();
        for(IRoom room : allRooms){
            if(room.getRoomNumber().equals(roomNumber)){
                System.out.println("Error! Room number already exists! Please create a different room.");
                return;
            }
        }
        System.out.println("Enter a room type: 1 for Single OR 2 for Double");
        String roomType = scanner.next();
        RoomType roomTypeOption = RoomType.getRoomType(Integer.parseInt(roomType));
        System.out.println("Assign price of room: ");
        double roomPrice = scanner.nextDouble();
        IRoom room = new Room(roomNumber, roomPrice, roomTypeOption);
        adminResource.addRoom(room);
    }
    private static void printAllRooms(){
        Collection<IRoom> allRooms = adminResource.getAllRooms();
        for(IRoom room : allRooms){
            System.out.println(room.toString());
        }
    }

    private static void printAllCustomers(){
        Collection<Customer> allCustomers = adminResource.getAllCustomers();
        for(Customer customer : allCustomers){
            System.out.println(customer.toString());
        }
    }
}