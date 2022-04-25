const app = angular.module("shopping-cart-app", []);

app.controller("shopping-cart-ctrl", function($scope, $http) {

	/* QUẢN LÝ GIỎ HÀNG */
	$scope.cart = {
		items: [],
		add(id) {
			var item = this.items.find(item => item.id == id)
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			
					Swal.fire({
					icon: 'success',
					title: 'Đã thêm vào giỏ hàng!',
					type: 'success'
				})
			
			} else {
				$http.get(`/rest/products/${id}`).then(resp => {
					resp.data.qty = 1;
					this.items.push(resp.data);
					this.saveToLocalStorage();
					
					Swal.fire({
						icon: 'success',
						title: 'Đã thêm vào giỏ hàng!',
						type: 'success'

					});
				})

			}
		},
		remove(id) {
			var index = this.items.findIndex(item => item.id = id);
			this.items.splice(index, 1);
			this.saveToLocalStorage();
		},
		clear() {
			this.items = [];
			this.saveToLocalStorage();
		},
		/*amt_of(item){},*/
		get count() {
			return this.items
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		get amount() {
			return this.items
				.map(item => item.qty * item.price)
				.reduce((total, qty) => total += qty, 0);
		},
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.items));
			localStorage.setItem("cart", json);
		},
		loadFormLocalStorage() {
			var json = localStorage.getItem("cart");
			this.items = json ? JSON.parse(json) : [];
		}
	}
	$scope.cart.loadFormLocalStorage();

	$scope.order = {
		createDate: new Date(),
		address: "",
		account: { username: $("#username").text() },
		get orderDetails() {
			return $scope.cart.items.map(item => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.qty
				}
			});
		},
		purchase() {
			var order = angular.copy(this);
			// thực hiện đặt hàng
			console.log(order);
			$http.post("/rest/orders", order).then(resp => {
				Swal.fire({
					icon: 'success',
					title: 'Đặt hàng thành công!',
					type: 'success'
				})
				$scope.cart.clear();
				location.href = "/order/detail/" + resp.data.id;

			}).catch(error => {
				Swal.fire({
					icon: 'error',
					title: 'Đặt hàng thất bại',
					type: 'error'
				})
				console.log(error)
			})
		}
	}
}); 