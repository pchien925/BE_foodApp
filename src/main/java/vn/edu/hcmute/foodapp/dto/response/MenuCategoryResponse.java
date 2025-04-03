package vn.edu.hcmute.foodapp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MenuCategoryResponse {
    private Integer id;
    private String name;
    private String imageUrl;
    private String description;
}
