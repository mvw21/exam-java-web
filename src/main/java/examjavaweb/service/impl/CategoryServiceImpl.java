package examjavaweb.service.impl;

import examjavaweb.model.entity.Category;
import examjavaweb.model.entity.CategoryName;
import examjavaweb.model.service.CategoryServiceModel;
import examjavaweb.repository.CategoryRepository;
import examjavaweb.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initCategories() {
        if(this.categoryRepository.count() == 0){
            Arrays.stream(CategoryName.values())
                    .forEach(categoryName -> {
                        this.categoryRepository
                                .save(new Category(categoryName,
                                        String.format("Description for %s",
                                                categoryName.name())));
                    });
        }
    }

    @Override
    public CategoryServiceModel findByCategoryName(CategoryName categoryName) {
        return this.categoryRepository
                .findByCategoryName(categoryName)
                .map(category -> this.modelMapper.map(category,CategoryServiceModel.class))
                .orElse(null);
    }

    @Override
    public CategoryServiceModel findByName(CategoryName name) {
        return this.modelMapper.map(this.categoryRepository.findByCategoryName(name).orElse(null), CategoryServiceModel.class);
    }

    @Override
    public Category find(CategoryName categoryName) {
        return this.categoryRepository
                .findByCategoryName(categoryName)
                .orElse(null);
    }
}
