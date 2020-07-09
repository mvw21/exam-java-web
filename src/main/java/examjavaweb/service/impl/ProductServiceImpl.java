package examjavaweb.service.impl;

import examjavaweb.model.entity.Category;
import examjavaweb.model.entity.CategoryName;
import examjavaweb.model.entity.Product;
import examjavaweb.model.service.ProductServiceModel;
import examjavaweb.model.view.ProductViewModel;
import examjavaweb.repository.ProductsRepository;
import examjavaweb.service.CategoryService;
import examjavaweb.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductsRepository productsRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductsRepository productsRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.productsRepository = productsRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addProduct(ProductServiceModel productServiceModel) {
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        product.setCategory(this.categoryService.find(productServiceModel.getCategory().getCategoryName()));
        this.productsRepository.saveAndFlush(product);
    }

    @Override
    public List<ProductViewModel> findAllItems() {
        return this.productsRepository
                .findAll()
                .stream()
                .map(product -> {
                    ProductViewModel productViewModel = this.modelMapper
                            .map(product, ProductViewModel.class);

                    productViewModel.setImgUrl(String.format("/img/%s.png",
                            product.getCategory().getCategoryName().name()));

                    return productViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductViewModel findById(String id) {
        return this.productsRepository
                .findById(id)
                .map(product -> {
                    ProductViewModel productViewModel = this.modelMapper
                            .map(product, ProductViewModel.class);

                    productViewModel.setImgUrl(String.format("/img/%s.png",
                            product.getCategory().getCategoryName().name()));

                    return productViewModel;
                })
                .orElse(null);
    }

    @Override
    public void delete(String id) {
        this.productsRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
       this.productsRepository.deleteAll();
    }

    @Override
    public BigDecimal getAllPrice() {

        return this.productsRepository.findAll()
                .stream()
                .map(x -> x.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Collection<ProductViewModel> getProducts(CategoryName categoryName) {
        return this.productsRepository.findAll()
                .stream()
                .filter(x -> x.getCategory().getCategoryName().equals(categoryName))
                .map(x -> this.modelMapper.map(x,ProductViewModel.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<ProductServiceModel> findAllByCategory(String categoryName) {
        Category category = this.modelMapper.map(this.categoryService.find(CategoryName.valueOf(categoryName)), Category.class);
        return this.productsRepository.findAllByCategory(category).stream()
                .map(product -> this.modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());
    }


}
