
app.controller("account-ctrl", function($scope, $http) {
	
	$scope.items = [];
	$scope.authorities=[];
	$scope.roles=[];
	$scope.form = {};
	//load toàn bộ thông tin từ server về
	$scope.initialize = function() {
		//load accounts
		
		$http.get("/rest/authorities/account").then(resp => {
			$scope.items = resp.data;
			
		})
			$http.get("/rest/roles").then(resp => {
			$scope.roles = resp.data;
			
		})
			
	}
	$scope.initialize();

	$scope.reset = function() {
		$scope.form = {
			photo: 'user.png'
		};

	}
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab('show');

	}
	
	$scope.create = function() {
		
		var item = angular.copy($scope.form);
		console.log(item.account);		
		$http.post(`localhost:8080/rest/accounts/create`, item.account)
		.catch(error => {
			alert("Lỗi thêm mới tài khoản!");
			console.log("Error", error);
		});
	}
	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/accounts/${item.account.username}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.username == item.account.username);
			$scope.items[index] = item ;
			alert("Cập nhật tài khoản thành công!");
		}).catch(error => {
			alert("Lỗi cập nhật tài khoản!");
			console.log("Error", error);
		});
			}
	$scope.delete = function(item) {
			
		$http.delete(`/rest/accounts/${item.username}`).then(resp => {
			var index = $scope.items.findIndex(p => p.username == item.username);
			$scope.items.splice(index,1) ;
			$scope.reset();
			alert("Xóa tài khoản thành công!");
		}).catch(error => {
			alert("Lỗi xóa tài khoản!");
			console.log("Error", error);
		});
			}
			
	$scope.imageChanged = function(files) {
		
				var data = new FormData();
				data.append('file', files[0]); // lấy file ngta chọn được bỏ vào trong data
				$http.post('/rest/upload/images', data, { // post lên server
					transformRequest: angular.identity,
					headers: { 'Content-Type': undefined }

				}).then(resp => {
					$scope.form.account.photo = resp.data.name; //lấy name của data thế vào form.image 
				}).catch(error => {
					alert("lỗi upload hình ảnh");
					console.log("Error:", error);
				})

			}
	
	$scope.pager = {
		
		page:0,
		size: 10,
		get items(){
			var start = this.page* this.size;
			return $scope.items.slice(start,start+ this.size);
		},
		get count(){
			return Math.ceil(1.0 * $scope.items.length / this.size);
		},
		first(){
			this.page = 0;
		},
		prev(){
			this.page--;
			if(this.page < 0){
				this.last();
			}
		},
		next(){
			this.page++;
			if(this.page >= this.count){
				this.first();
			}
		},
		last(){
			this.page= this.count- 1;
		}
	} 
	
});