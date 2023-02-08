package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class HotelResource {
    //Task 1: Provide static reference
    private static HotelResource INSTANCE;

    public static HotelResource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HotelResource();
        }
        return INSTANCE;
    }

    private HotelResource() {
    }

    //Task 2 = Method for getCustomer
    private static final CustomerService customerService = CustomerService.getInstance();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    //Task 3 = Method for createACustomer
    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    //Task 4 = Method for getRoom
    private final ReservationService reservationService = ReservationService.getInstance();

    public IRoom getRoom(Integer roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    //Task 5 = Method for bookARoom
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    //Task 6 = Collection of Reservation
    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = getCustomer(customerEmail);
        if (customer == null) {
            return new ArrayList<Reservation>();
        } else {
            return reservationService.getCustomersReservation(customer);
        }
    }

    //Task 7 = Collection for findARoom
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

    //Task 7 CONT = Collection for alternative rooms
    public Collection<IRoom> findAlternativeRooms(final Date checkIn, final Date checkOut) {
        return reservationService.findRoomsOnAlternativeDates(checkIn, checkOut);
    }
}
