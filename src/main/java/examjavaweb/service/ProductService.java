package examjavaweb.service;

import examjavaweb.model.entity.CategoryName;
import examjavaweb.model.service.ProductServiceModel;
import examjavaweb.model.view.ProductViewModel;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface ProductService {
    void addProduct(ProductServiceModel productServiceModel);

    List<ProductViewModel> findAllItems();

    ProductViewModel findById(String id);

    void delete(String id);

    void deleteAll();

    BigDecimal getAllPrice();

    Collection<ProductViewModel> getProducts(CategoryName categoryName);

    List<ProductServiceModel> findAllByCategory(String categoryName);
}
