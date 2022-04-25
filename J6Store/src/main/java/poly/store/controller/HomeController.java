package poly.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping({ "/", "/home/index" ,"/index"})
	public String home() {
		return "/layout/home";
//		return "redirect:/product/list";
	}

	/*
	 * @RequestMapping({ "/product/list" }) public String product() { return
	 * "/product/list"; }
	 */
	
	@RequestMapping({ "/admin", "/admin/home/index" })
	public String admin() {
		return "redirect:/assets/admin/index.html";
	}
}
