package vn.edu.hcmute.foodapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.hcmute.foodapp.dto.request.MenuItemOptionRequest;
import vn.edu.hcmute.foodapp.dto.response.MenuItemOptionResponse;
import vn.edu.hcmute.foodapp.dto.response.OptionResponse;
import vn.edu.hcmute.foodapp.entity.MenuItemOption;
import vn.edu.hcmute.foodapp.exception.ResourceNotFoundException;
import vn.edu.hcmute.foodapp.mapper.MenuItemOptionMapper;
import vn.edu.hcmute.foodapp.mapper.OptionMapper;
import vn.edu.hcmute.foodapp.repository.MenuItemOptionRepository;
import vn.edu.hcmute.foodapp.repository.MenuItemRepository;
import vn.edu.hcmute.foodapp.repository.OptionRepository;
import vn.edu.hcmute.foodapp.service.MenuItemOptionService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuItemOptionServiceImpl implements MenuItemOptionService {
    private final MenuItemOptionRepository menuItemOptionRepository;
    private final MenuItemRepository menuItemRepository;
    private final OptionRepository optionRepository;

    @Override
    public MenuItemOptionResponse createMenuItemOption(MenuItemOptionRequest request) {
        log.info("Create MenuItemOption with name: {}", request);
        MenuItemOption menuItemOption = MenuItemOptionMapper.INSTANCE.toEntity(request);

        menuItemOption.setMenuItem(
                menuItemRepository.findById(request.getMenuItemId())
                        .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + request.getMenuItemId()))
        );
        menuItemOption.setOption(
                optionRepository.findById(request.getOptionId())
                        .orElseThrow(() -> new ResourceNotFoundException("Option not found with id: " + request.getOptionId()))
        );
        return MenuItemOptionMapper.INSTANCE.toResponse(menuItemOptionRepository.save(menuItemOption));
    }

    @Override
    public MenuItemOptionResponse updateMenuItemOption(Integer id, MenuItemOptionRequest request) {
        log.info("Update MenuItemOption with id: {}", id);
        MenuItemOption menuItemOption = menuItemOptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item option not found with id: " + id));

        MenuItemOptionMapper.INSTANCE.update(request, menuItemOption);
        return MenuItemOptionMapper.INSTANCE.toResponse(menuItemOptionRepository.save(menuItemOption));
    }

    @Override
    public void deleteMenuItemOption(Integer id) {
        log.info("Delete MenuItemOption with id: {}", id);
        if (!menuItemOptionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu item option not found with id: " + id);
        }
        menuItemOptionRepository.deleteById(id);
    }

    @Override
    public MenuItemOptionResponse getMenuItemOptionById(Integer id) {
        log.info("Get MenuItemOption with id: {}", id);
        MenuItemOption menuItemOption = menuItemOptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item option not found with id: " + id));
        return MenuItemOptionMapper.INSTANCE.toResponse(menuItemOption);
    }

}
