package poly.store.service;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import poly.store.entity.Product;

public interface ProductService {

	List<Product> findAll();

	Page<Product> findProducts( Pageable pageable);
	
	Product findById(Integer id);

	Page<Product> findByCategoryId(String cid,Pageable pageable);

	Product create(Product product);

	Product update(Product product);

	void delete(Integer id);

}
