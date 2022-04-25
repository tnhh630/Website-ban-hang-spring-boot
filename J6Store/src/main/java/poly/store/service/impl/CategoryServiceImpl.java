package poly.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poly.store.dao.CategoryDAO;
import poly.store.entity.Category;
import poly.store.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService{
@Autowired
CategoryDAO cdao;

@Override
public List<Category> findAll() {
	// TODO Auto-generated method stub
	return cdao.findAll();
}

@Override
public Category findById(String id) {
	// TODO Auto-generated method stub
	return cdao.findById(id).get();
}
@Override
public Category create(Category category) {
	// TODO Auto-generated method stub
	return cdao.save(category);
}

@Override
public Category update(Category category) {
	// TODO Auto-generated method stub
	return cdao.save(category);
}

@Override
public void delete(String id) {
	// TODO Auto-generated method stub
	 cdao.deleteById(id);;
}



}
