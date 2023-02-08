package model;
import java.util.Date;

public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate ){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
    public IRoom getRoom(){
        return this.room;
    }

    public Date getCheckInDate(){
        return this.checkInDate;
    }
    public Date getCheckOutDate(){
        return this.checkOutDate;
    }
    @Override
    public String toString(){
        return "Customer: " + this.customer.toString() + "\nRoom: " + this.room.toString() + "\nCheckIn Date: " + this.checkInDate + "\nCheckOut Date: " + this.checkOutDate;
    }
    @Override
    public boolean equals(Object obj){
        if(this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof Reservation)){
            return false;
        }
        Reservation other = (Reservation) obj;
        if(this.checkInDate.before(other.checkOutDate) && this.room.getRoomNumber().equals(other.room.getRoomNumber())){
            return true;
        } else{
            return false;
        }
    }
}
