package examjavaweb.repository;

import examjavaweb.model.entity.Category;
import examjavaweb.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Product,String> {
    List<Product> findAllByCategory(Category category);
    Optional<Product> findByName(String name);
}
