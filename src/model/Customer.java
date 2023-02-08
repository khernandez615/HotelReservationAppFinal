package model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    //Private final String variables will protect User data.
    private final String firstName;
    private final String lastName;
    private final String email;

    //Constructor - Customer
    public Customer(String firstName, String lastName, String email){
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        if(isValid(email)){
            this.email = email;
        }
        else{
            throw new IllegalArgumentException("You have not entered a valid email format. Please use: email@domain.com");
        }
    }
    //Regex email pattern options on Stack Overflow
    private final String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private final Pattern validPattern = Pattern.compile(emailPattern);

    private boolean isValid(String email) {
        Matcher matcher = validPattern.matcher(email);
        return matcher.matches();
    }
    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString(){
        return "First Name: " + this.firstName + " Last Name: " + this.lastName + "  Email: "+ this.email;
    }

}
