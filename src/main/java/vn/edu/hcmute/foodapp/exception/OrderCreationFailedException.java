package vn.edu.hcmute.foodapp.exception;

public class OrderCreationFailedException extends RuntimeException {
    public OrderCreationFailedException(String message) {
        super(message);
    }
}
