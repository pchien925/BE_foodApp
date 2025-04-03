package vn.edu.hcmute.foodapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.request.OptionRequest;
import vn.edu.hcmute.foodapp.dto.response.MenuItemResponse;
import vn.edu.hcmute.foodapp.dto.response.OptionResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;
import vn.edu.hcmute.foodapp.entity.Option;
import vn.edu.hcmute.foodapp.exception.ResourceNotFoundException;
import vn.edu.hcmute.foodapp.mapper.MenuItemMapper;
import vn.edu.hcmute.foodapp.mapper.OptionMapper;
import vn.edu.hcmute.foodapp.repository.OptionRepository;
import vn.edu.hcmute.foodapp.service.OptionService;
import vn.edu.hcmute.foodapp.util.PaginationUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class OptionServiceImpl implements OptionService {
    private final OptionRepository optionRepository;

    @Override
    public OptionResponse createOption(OptionRequest request) {
        log.info("Creating new Option with request: {}", request);
        Option option = OptionMapper.INSTANCE.toEntity(request);
        Option savedOption = optionRepository.save(option);
        log.info("Successfully created Option with id: {}", savedOption.getId());
        return OptionMapper.INSTANCE.toResponse(savedOption);
    }

    @Override
    @Transactional
    public OptionResponse updateOption(Integer id, OptionRequest request) {
        log.info("Attempting to update Option with id: {}", id);
        Option existingOption = optionRepository.findById(id)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Option not found with id: " + id);
                });

        OptionMapper.INSTANCE.update(request, existingOption);
        Option updatedOption = optionRepository.save(existingOption);
        log.info("Successfully updated Option with id: {}", id);
        return OptionMapper.INSTANCE.toResponse(updatedOption);
    }

    @Override
    @Transactional
    public void deleteOption(Integer id) {
        log.info("Attempting to delete Option with id: {}", id);
        if (!optionRepository.existsById(id)) {
            log.error("Option not found for deletion with id: {}", id);
            throw new ResourceNotFoundException("Option not found with id: " + id);
        }
        optionRepository.deleteById(id);
        log.info("Successfully deleted Option with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public OptionResponse getOptionById(Integer id) {
        log.info("Fetching Option with id: {}", id);
        Option option = optionRepository.findById(id)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Option not found with id: " + id);
                });
        log.info("Successfully fetched Option with id: {}", id);
        return OptionMapper.INSTANCE.toResponse(option);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<OptionResponse> getOptions(int page, int size, String sort, String direction) {
        log.info("Fetching all options with pagination: page={}, size={}, sort={}, direction={}", page, size, sort, direction);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<Option> optionPage = optionRepository.findAll(pageable);

        return PageResponse.<OptionResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(optionPage.getTotalPages())
                .totalElements(optionPage.getTotalElements())
                .content(optionPage.getContent().stream()
                        .map(OptionMapper.INSTANCE::toResponse)
                        .toList())
                .build();
    }

}
