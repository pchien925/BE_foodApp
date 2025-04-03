package vn.edu.hcmute.foodapp.service;

import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.request.OptionRequest;
import vn.edu.hcmute.foodapp.dto.response.OptionResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;

public interface OptionService {
    OptionResponse createOption(OptionRequest request);

    OptionResponse updateOption(Integer id, OptionRequest request);

    void deleteOption(Integer id);

    OptionResponse getOptionById(Integer id);

    PageResponse<OptionResponse> getOptions(int page, int size, String sort, String direction);
}
