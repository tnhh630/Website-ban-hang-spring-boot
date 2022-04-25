package poly.store.service;

import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import poly.store.entity.Account;
import poly.store.entity.Product;


public interface AccountService {

	public Account findById(String username);

	public List<Account> getAdministrations();

	public List<Account> findAll();
	
	public void loginFromOAuth2(OAuth2AuthenticationToken oauth2);
	
	public 	Account create(Account account);
	
	Account update(Account account);

	void delete(String username);
}
