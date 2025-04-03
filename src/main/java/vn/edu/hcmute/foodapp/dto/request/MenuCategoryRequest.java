package vn.edu.hcmute.foodapp.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class MenuCategoryRequest {
    @NotEmpty(message = "name must not be empty")
    private String name;

    private String description;

    private String imageUrl;

}
