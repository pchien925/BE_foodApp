package com.foodApp.service.impl;

import com.foodApp.dto.request.OptionTypeRequest;
import com.foodApp.dto.request.OptionValueRequest;
import com.foodApp.dto.response.OptionTypeResponse;
import com.foodApp.dto.response.OptionValueResponse;
import com.foodApp.entity.OptionType;
import com.foodApp.entity.OptionValue;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.mapper.OptionTypeMapper;
import com.foodApp.mapper.OptionValueMapper;
import com.foodApp.repository.OptionTypeRepository;
import com.foodApp.repository.OptionValueRepository;
import com.foodApp.service.OptionTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionTypeServiceImpl implements OptionTypeService {
    private final OptionTypeRepository optionTypeRepository;
    private final OptionTypeMapper optionTypeMapper;
    private final OptionValueRepository optionValueRepository;
    private final OptionValueMapper optionValueMapper;

    @Override
    public OptionType findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return optionTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OptionType not found"));
    }

    @Override
    public OptionTypeResponse getOptionType(Long id) {
        return optionTypeMapper.toResponse(findById(id));
    }

    @Transactional
    @Override
    public OptionTypeResponse createOptionType(OptionTypeRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        OptionType optionType = optionTypeMapper.toEntity(request);

        return optionTypeMapper.toResponse(optionTypeRepository.save(optionType));
    }

    @Transactional
    @Override
    public OptionTypeResponse updateOptionType(Long id, OptionTypeRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        OptionType optionType = findById(id);
        optionTypeMapper.update(request, optionType);

        OptionType updatedOptionType = optionTypeRepository.save(optionType);

        return optionTypeMapper.toResponse(updatedOptionType);
    }

    @Transactional
    @Override
    public void deleteOptionType(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        OptionType optionType = findById(id);
        optionTypeRepository.delete(optionType);
    }

    @Override
    public List<OptionTypeResponse> getAllOptionTypes() {
        return optionTypeRepository.findAll().stream()
                .map(optionTypeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OptionValueResponse createOptionValue(Long id, OptionValueRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        OptionType optionType = findById(id);

        OptionValue optionValue = optionValueMapper.toEntity(request);

        optionValue.setOptionType(optionType);

        return optionValueMapper.toResponse(optionValueRepository.save(optionValue));
    }

    @Override
    public OptionValueResponse updateOptionValue(Long id, Long valueId, OptionValueRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (valueId == null) {
            throw new IllegalArgumentException("ValueId cannot be null");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        OptionType optionType = findById(id);
        OptionValue optionValue = optionType.getOptionValues().stream()
                .filter(value -> value.getId().equals(valueId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("OptionValue not found"));

        optionValueMapper.update(request, optionValue);

        return optionValueMapper.toResponse(optionValueRepository.save(optionValue));
    }

    @Override
    public List<OptionValueResponse> getOptionValues(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        OptionType optionType = findById(id);

        return optionType.getOptionValues().stream()
                .map(optionValueMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOptionValue(Long id, Long valueId) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (valueId == null) {
            throw new IllegalArgumentException("ValueId cannot be null");
        }

        OptionType optionType = findById(id);
        OptionValue optionValue = optionType.getOptionValues().stream()
                .filter(value -> value.getId().equals(valueId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("OptionValue not found"));

        optionValueRepository.delete(optionValue);
    }
}
