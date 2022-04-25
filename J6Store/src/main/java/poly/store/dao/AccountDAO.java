package poly.store.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import poly.store.entity.Account;

public interface  AccountDAO extends JpaRepository<Account,String>{
	//DISTINCT LOẠI BỎ BẢN GHI TRÙNG LẶP
	@Query("SELECT DISTINCT ar.account FROM Authority ar WHERE ar.role.id IN ('DIRE','STAF')")
	List<Account> getAdministrations();
	
	

}

