package vn.edu.hcmute.foodapp.dto.request;

import lombok.Getter;

@Getter
public class WishlistItemRequest {
    private Long userId;
    private Long menuItemId;
}
