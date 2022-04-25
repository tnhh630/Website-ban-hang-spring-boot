package poly.store.service;

import java.util.List;

import poly.store.entity.Category;
import poly.store.entity.Product;

public interface CategoryService {
List<Category> findAll();

Category create(Category category);

Category findById(String id);

Category update(Category ategory);

void delete(String id);
}
