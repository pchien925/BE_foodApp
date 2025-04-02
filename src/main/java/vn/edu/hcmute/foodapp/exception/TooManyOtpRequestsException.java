package vn.edu.hcmute.foodapp.exception;

public class TooManyOtpRequestsException extends RuntimeException {
    public TooManyOtpRequestsException(String message) {
        super(message);
    }
}
