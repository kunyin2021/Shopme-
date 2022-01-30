package com.shopme.admin.user;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userKun = new User("kunyin2@illinois.edu", "kun2022", "Kun", "Yin");
		userKun.addRole(roleAdmin);
		
		User savedUser = repo.save(userKun);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userCam = new User("cameron@gmail.com", "cam2022", "Cameron", "Tucker");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userCam.addRole(roleEditor);
		userCam.addRole(roleAssistant);
		
		User savedUser = repo.save(userCam);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
		
	}
	
	@Test
	public void testGetUserById() {
		User userKun = repo.findById(1).get();
		System.out.println(userKun);
		assertThat(userKun).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userKun = repo.findById(1).get();
		userKun.setEnabled(true);
		userKun.setEmail("kunjavaprogrammer@gmail.com");
		
		repo.save(userKun);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userCam = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(6);
		
		userCam.getRoles().remove(roleEditor);
		userCam.addRole(roleSalesperson);
		
		repo.save(userCam);
	}
	
	@Test
	public void testDeletedUser() {
		Integer userId = 2;
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "cameron@gmail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		//Integer id = 100;
		Integer id = 1;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
		
	}
	@Test
	public void testEnableUser() {
		Integer id = 6;
		repo.updateEnabledStatus(id, true);
		
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 1;
		int pageSize = 4;
		
		PageRequest pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
	
	@Test
	public void testSearchUsers() {
		String keyword = "bruce";
		
		int pageNumber = 0;
		int pageSize = 4;
		
		PageRequest pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword, pageable);
		
		List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isGreaterThan(0);
	}
	
}
