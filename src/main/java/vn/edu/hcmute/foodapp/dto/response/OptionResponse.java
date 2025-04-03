package vn.edu.hcmute.foodapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionResponse {
    private Integer id;

    private String name;

    private String description;

    private List<MenuItemOptionResponse> menuItemOption;
}
