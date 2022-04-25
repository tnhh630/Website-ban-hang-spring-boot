package poly.store.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import poly.store.entity.Product;

public interface  ProductDAO extends JpaRepository<Product,Integer>{
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE ?1")
	Page<Product> findByKeyword(String keyword , Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE p.category.id=?1")
	Page<Product> findByCategoryId(String cid , Pageable pageable);

	// @Query(value="SELECT id,Name,Image,Price,CreateDate,Available,CategoryId FROM Products where id > 1000 and id < 1021 ",nativeQuery = true)
//	@Query(value = "{CALL sp_select20Product()}", nativeQuery = true)
//	List<Product> find20();
//	Pageable : vị trí trang đc lấy
	@Query("SELECT p FROM Product p ")
	Page<Product> findProducts( Pageable pageable);

}

