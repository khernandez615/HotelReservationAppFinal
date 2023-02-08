package service;

import model.Customer;

import java.util.Collection;
import java.util.LinkedList;

public class CustomerService {
    private static CustomerService INSTANCE;

    private CustomerService(){}
    private static final LinkedList<Customer> customers = new LinkedList<>();

    public static CustomerService getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new CustomerService();
        }
        return INSTANCE;
    }
//ADD CUSTOMER
    public void addCustomer(String email, String firstName, String lastName){
        try {
            Customer customer = new Customer(email, firstName, lastName);
            customers.add(customer);
        } catch (IllegalArgumentException e) {
            System.out.println("Your account cannot be created. Please check that you have entered a valid email: name@domain.com ");
        }
    }
//GETTERS
    public Customer getCustomer (String customerEmail){
        for(int i = 0; i< customers.size(); i++){
            Customer customer = customers.get(i);
            if (customerEmail.equals(customer.getEmail())){
                return customer;
            }
        }
        //If a customer email is not found return null
        return null;
    }
    public Collection<Customer> getAllCustomers(){
        return customers;
    }
}
