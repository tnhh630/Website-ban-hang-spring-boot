var app = angular.module("admin-app",["ngRoute"]);

	app.config(function($routeProvider){
		$routeProvider
		.when("/product",{
			templateUrl: "/assets/admin/product/index.html",
			controller: "product-ctrl"
		})
		.when("/category",{
			templateUrl: "/assets/admin/category/index.html",
			controller: "category-ctrl"
		})
		.when("/authorize",{
			templateUrl: "/assets/admin/authority/index.html",
			controller: "auth-ctrl"
		})
		.when("/unauthorized",{
			templateUrl: "/assets/admin/authority/unauthorized.html",
			controller: "authority-ctrl"
		})
		.when("/account",{
			templateUrl: "/assets/admin/account/index.html",
			controller: "account-ctrl"
		})
		.otherwise({
			template: "<h1 class='text-center'>FPT POLYTECHNIC ADMIN </h1>"
		})
	});