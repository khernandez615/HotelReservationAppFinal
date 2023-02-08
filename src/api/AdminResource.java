package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;

public class AdminResource {
    private AdminResource() {}
    //Task 1: Create a static reference
    private static final AdminResource INSTANCE = new AdminResource();
    public static AdminResource getInstance() {
        return INSTANCE;
    }
    //Task 2: Create public Customer getCustomer by passing in email.
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }
    //Task 3: Create public void addRoom
    public void addRoom(IRoom room) {
            reservationService.addRoom(room);
        }
    //Display all rooms and all customers
    private final ReservationService reservationService = ReservationService.getInstance();
    public Collection<IRoom> getAllRooms() {
        return reservationService.listOfRooms();
    }
    private final CustomerService customerService = CustomerService.getInstance();
    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    //Display all reservations
    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}
