package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ReservationService {
    private static ReservationService INSTANCE;

    public static ReservationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReservationService();
        }
        return INSTANCE;
    }

    public LinkedList<IRoom> listOfRooms = new LinkedList<>();

    public void addRoom(IRoom room) {
        listOfRooms.add(room);
    }

    public IRoom getARoom(Integer roomNumber) {
        for (int i = 0; i < listOfRooms.size(); i++) {
            IRoom room = listOfRooms.get(i);
            if (roomNumber.equals(room.getRoomNumber())) {
                return room;
            }
        }
        //If a room number is not found return null
        return null;
    }

    //Reserve a room - updates mapOfReservation with new reservations for the user.
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        if (!isReservationValid(reservation)){
            System.out.println("Error. Please select an available room from the list.");
            return null;
        } else {
            Collection<Reservation> customersReservation = getCustomersReservation(customer);
            if (customersReservation == null) {
                ArrayList<Reservation> listOfReservations = new ArrayList<>();
                listOfReservations.add(reservation);
                mapOfReservation.put(customer.getEmail(), listOfReservations);
                setOfReservations.add(reservation);
            } else {
                customersReservation.add(reservation);
                mapOfReservation.put(customer.getEmail(), customersReservation);
                setOfReservations.add(reservation);
            }
            return reservation;
        }
    }

    public Collection<IRoom> findRooms(Date checkIn, Date checkOut) {
        //Check if the CheckOut date is before now
        //Check if the CheckIn date equals the CheckOut date
        //Check if the CheckIn is after CheckOut date
        ArrayList<IRoom> unavailableRooms = new ArrayList<>();
        if (checkIn.before(new Date()) || checkIn.after(checkOut) || checkIn.equals(checkOut)) {
            System.out.println("Your selected dates are invalid. Please fix.");
            return unavailableRooms;
        } else {
            //Loop through the Reservations
            Collection<Reservation> allReservations = new ArrayList<>();
            for (Collection<Reservation> reservations : mapOfReservation.values()) {
                for (Reservation currentReservation : reservations) {
                    allReservations.add(currentReservation);
                }
            }
            //Check if the Reservation overlaps -requested date is before the reservation checkout.
            //If true, add the room to a collection of unavailable rooms.
            for (Reservation reservation : allReservations) {
                if (checkIn.before(reservation.getCheckOutDate())){
                    unavailableRooms.add(reservation.getRoom());
                }
            }

            ArrayList<IRoom> availableRooms = new ArrayList<>();
            for (IRoom room : listOfRooms) {
                boolean isRoomAvailable = true;
                for (IRoom unavailableRoom : unavailableRooms) {
                    if (room.getRoomNumber().equals(unavailableRoom.getRoomNumber())) {
                        isRoomAvailable = false;
                    }
                }
                if (isRoomAvailable) {
                    availableRooms.add(room);
                }
            }
            return availableRooms;
        }
    }

    //Accepts parameter customer obj, returns collection of reservations
    private final Map<String, Collection<Reservation>> mapOfReservation = new HashMap<>();
    private final Collection<Reservation> setOfReservations = new HashSet<>();


    public Collection<Reservation> getCustomersReservation(Customer customer) {
        String email = customer.getEmail();
        return mapOfReservation.get(email);
    }

    public void printAllReservation() {
        Collection<Reservation> allReservations = new ArrayList<>();
        for (Collection<Reservation> reservations : mapOfReservation.values()) {
            for (Reservation currentReservation : reservations) {
                allReservations.add(currentReservation);
            }
        }
        for (Reservation reservation : allReservations) {
            System.out.println(reservation.toString());
        }
    }

    private boolean isReservationValid(Reservation res) {
        Collection<Reservation> allReservations = new ArrayList<>();
        for (Collection<Reservation> reservations : mapOfReservation.values()) {
            for (Reservation currentReservation : reservations) {
                allReservations.add(currentReservation);
            }
        }
        for (Reservation reservation : allReservations) {
            if (res.getCheckInDate().before(reservation.getCheckOutDate()) && res.getRoom().getRoomNumber().equals(reservation.getRoom().getRoomNumber())) {
                return false;
            }
        }
        return true;
    }

    public Collection<IRoom> findRoomsOnAlternativeDates(Date checkIn, Date checkOut) {
        //Convert checkIn and checkOut dates to LocalDate which does not factor time and add 7 days to each. Referred to StackOverflow
        LocalDate checkInPlus7 = LocalDate.ofInstant(checkIn.toInstant(), ZoneId.systemDefault()).plusDays(7);
        Date checkInPlus7Date = Date.from(checkInPlus7.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate checkOutPlus7 = LocalDate.ofInstant(checkOut.toInstant(), ZoneId.systemDefault()).plusDays(7);
        Date checkOutPlus7Date = Date.from(checkOutPlus7.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return findRooms(checkInPlus7Date, checkOutPlus7Date);
    }

    public Collection<IRoom> listOfRooms() {
        return listOfRooms;
    }
}
