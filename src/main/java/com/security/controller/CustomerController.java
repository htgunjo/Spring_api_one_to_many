package com.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.security.model.Customer;
import com.security.repository.CustomerRepository;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	AuthenticationManagerBuilder authenticationManagerBuilder;

	@GetMapping("/customers")
	public List<Customer> getAllcustomers() {
		return customerRepository.findAll();
	}

	@GetMapping("/customers/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Customer getCustomerByID(@PathVariable(value = "id") long id) throws Exception {
		return customerRepository.findById(id).orElseThrow(() -> new Exception("Customer By the ID not found " + id));
	}

	@PostMapping("/customers")
	public String addCustomerByAdmin(@RequestBody Customer customer) {
		customerRepository.save(customer);
		return "Customer(s) Added Successfully";
	}

	@PutMapping("/customers/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Customer updateCustomerDetails(@RequestBody Customer customer, @PathVariable(value = "id") long id)
			throws Exception {

		return customerRepository.findById(id).map(update -> {
			update.setCustomer_fullName(customer.getCustomer_fullName());
			update.setCustomer_info(customer.getCustomer_info());

			return customerRepository.save(customer);
		}).orElseThrow(() -> new Exception("Customer not found by id"));
	}

	@DeleteMapping("/customers/{id}")
	public Map<String, String> deleteCustomer(@PathVariable(value = "id") long id) throws Exception {
		// Read the DB and find the id first.. then delete it
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new Exception("Customer by this id number is not found " + id));

		customerRepository.delete(customer);
		Map<String, String> response = new HashMap<>();
		response.put("Customer is Deleted", "Requested Customer removed " + id);
		return response;
	}

}
