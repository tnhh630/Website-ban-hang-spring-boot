package poly.store.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.store.entity.Account;
import poly.store.entity.Authority;

public interface  AuthorityDAO extends JpaRepository<Authority,Integer>{
	// lấy các quyền được cấp mà những cái account nằm trong nhóm các tài khoản admin
	@Query("SELECT DISTINCT a FROM Authority a WHERE a.account IN ?1")
	List<Authority> authoritiesOf(List<Account> accounts);

}

