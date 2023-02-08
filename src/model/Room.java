package model;
import java.util.Objects;

public class Room implements IRoom {
    private final Integer roomNumber;
    private final RoomType roomType;
    private final Double roomPrice;

    private final boolean isFree;

    //Room Constructor
    public Room(Integer roomNumber, Double roomPrice, RoomType roomType){
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
        if(roomPrice == 0.0) {
            isFree = true;
        } else {
            isFree = false;
        }
    }
    public Integer getRoomNumber() {
        return Integer.valueOf(this.roomNumber);
    }
    public Double getRoomPrice(){
        return this.roomPrice;
    }
    public RoomType getRoomType(){
        return this.roomType;
    }
    public boolean isFree() {
        return isFree;
    }

    @Override
    public String toString(){
        return "Room Number: " + this.roomNumber + " Price: $" + this.roomPrice +"0" + " Room Type: " + this.roomType;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(!(obj instanceof Room)) {
            return false;
        }

        return roomNumber.equals(this.roomNumber) && roomType == this.roomType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, roomType);
    }
}
