package com.shopme.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestControoler {

	@Autowired private CustomerService service;
	
	@PostMapping("/customers/check_unique_email")
	public String checkDuplicatEmail(String email) {
		return service.isEmailUnique(email) ? "OK" : "Duplicated";
	}
}
