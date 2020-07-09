package examjavaweb.service;

import examjavaweb.model.entity.Category;
import examjavaweb.model.entity.CategoryName;
import examjavaweb.model.service.CategoryServiceModel;

public interface CategoryService {
    void initCategories();

    CategoryServiceModel findByCategoryName(CategoryName categoryName);
    CategoryServiceModel findByName(CategoryName name);
    Category find(CategoryName categoryName);
}
