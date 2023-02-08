package model;
//Create enum RoomType and apply user input to assign SINGLE or DOUBLE *** Use of Integer
public enum RoomType {
    SINGLE(1),
    DOUBLE(2);

    public final Integer menuInput;

    RoomType(Integer menuInput) {
        this.menuInput = menuInput;
    }

    public static RoomType getRoomType(Integer menuInput) {
        switch(menuInput){
            case 1:
                return SINGLE;
            case 2:
                return DOUBLE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
