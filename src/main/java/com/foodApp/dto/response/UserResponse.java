package com.foodApp.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResponse {
    private Long id;

    private String username;

    private String email;

    private String password;

    private String phone;

    private String lastName;

    private String firstName;

    private Date dob;

    private String gender;

    private String status;

}
