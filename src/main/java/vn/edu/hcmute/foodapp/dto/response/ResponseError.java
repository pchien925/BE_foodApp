package vn.edu.hcmute.foodapp.dto.response;

public class ResponseError extends ResponseData {
    public ResponseError(int status, String message) {
        super(status, message);
    }
}
