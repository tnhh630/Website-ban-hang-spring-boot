package poly.store.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import poly.store.dao.ProductDAO;
import poly.store.entity.Product;
import poly.store.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService{
@Autowired
ProductDAO pdao;

@Override
public List<Product> findAll() {
	// TODO Auto-generated method stub
	return pdao.findAll();
}

@Override
public Product findById(Integer id) {
	// TODO Auto-generated method stub
	return pdao.findById(id).get();
}

@Override
public Page<Product> findByCategoryId(String cid ,Pageable pageable) {
	
	return pdao.findByCategoryId(cid, pageable);
}

@Override
public Product create(Product product) {
	// TODO Auto-generated method stub
	return pdao.save(product);
}

@Override
public Product update(Product product) {
	// TODO Auto-generated method stub
	return pdao.save(product);
}

@Override
public void delete(Integer id) {
	// TODO Auto-generated method stub
	 pdao.deleteById(id);;
}

@Override
public Page<Product> findProducts(Pageable pageable) {
	// TODO Auto-generated method stub
	return pdao.findProducts( pageable);
}
}
