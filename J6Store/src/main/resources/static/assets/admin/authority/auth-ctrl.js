
app.controller("auth-ctrl",function($scope,$http,$location){
	
	$scope.roles=[];
	$scope.authorities =[];
	$scope.admins = [];
	
	$scope.initialize1 = function(){
		// load all roles
			
		$http.get("/rest/roles").then(resp => {
			$scope.roles = resp.data;
		})
		// load staffs and directors (admin)
		$http.get("/rest/accounts?admin=true").then(resp => {
			
			$scope.admins = resp.data ;
		})
		// tải các quyền được cấp của các tài khoản admin
		$http.get("/rest/authorities?admin=true").then(resp => {			
			$scope.authorities = resp.data;
			
		}).catch( error => {
			$location.path("/unauthorized");
			console.log(error);
		})
	}
	
	
	$scope.authority_of = function(acc,role){
		if($scope.authorities){
			return $scope.authorities.find(ur => ur.account.username == acc.username && ur.role.id == role.id);
		}
	}
	$scope.authority_changed = function(acc,role){
		var  authority = $scope.authority_of(acc,role);
		if(authority){ // nếu đã cấp quyền => thu hồi quyền(xoá)
			$scope.revoke_authority(authority);
		}else{
			authority = {account: acc , role : role};
			$scope.grant_authority(authority);
		}
	}
	$scope.grant_authority = function(authority){
		$http.post(`/rest/authorities`,authority).then( resp => {
			$scope.authorities.push(resp.data);
			alert("cấp quyền thành công");
		}).catch(error => {
			alert("cấp quyền thất bại");	
			console.log("error: " , error);
		})
	}
	$scope.revoke_authority = function(authority){
		$http.delete(`/rest/authorities/${authority.id}`).then(resp => {
			var index = $scope.authorities.findIndex(a=> a.id == authority.id);
			$scope.authorities.splice(index,1);
			alert("thu hồi quyền thành công");
			
		}).catch(error => {
			alert("thu hồi quyền thất bại");
			console.log("error: ", error);
		})
	}
	$scope.initialize1();
});