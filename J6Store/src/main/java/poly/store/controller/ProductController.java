package poly.store.controller;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poly.store.dao.ProductDAO;
import poly.store.entity.Product;
import poly.store.service.ProductService;
import poly.store.service.SessionService;

@Controller
public class ProductController {
	int index = 1;
	@Autowired
	ProductService productService;
	@Autowired
	ProductDAO dao;
	@Autowired
	SessionService session;
	@RequestMapping("/product/list")
	public String list(Model model, @RequestParam("cid") Optional<String> cid,
			@RequestParam("p") Optional<Integer> p,
			@RequestParam("keywords") Optional<String> kw) {

		String kwords = kw.orElse(session.get("keywords", ""));
		session.set("keywords", kwords);
		Pageable pageable = PageRequest.of(p.orElse(0), 20);
		Page<Product> page = dao.findByKeyword("%" + kwords + "%", pageable);
		model.addAttribute("page", page);
		if(cid.isPresent()) {
		
			Page<Product> list = productService.findByCategoryId(cid.get(),pageable);
			model.addAttribute("page",list);
		}
//			else {	
//		
//			Page<Product> page1 = productService.findProducts(pageable);
//			model.addAttribute("page",page1);
//			return "product/list";
//		}
	
		return "product/list";
	}
	
	@RequestMapping("/product/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Product item = productService.findById(id);
		model.addAttribute("item",item);
		return "product/detail";
	}

}
