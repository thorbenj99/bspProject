package com.orderserv.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class OrderserveApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderserveApplication.class, args);
	}

	public CustomerHome postCustomerHome(CustomerHome newCustomerHome) {
		newCustomerHome.setId("9");
		return newCustomerHome;
	}
}

class CustomerHome {

	private final String id;
	private String name;

	public CustomerHome(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String s) {
	}
}

	

@RestController
@RequestMapping("/customers")
class RestHomeController {
	private List<CustomerHome> customers = new ArrayList<>();


	public RestHomeController() {
		customers.addAll(List.of(
				new CustomerHome("1", "Meyer"),
				new CustomerHome("2", "Öztürk"),
				new CustomerHome("3", "Schulter"),
				new CustomerHome("4", "Koch"),
				new CustomerHome("5", "Spieker"),
				new CustomerHome("6", "Johannsen"),
				new CustomerHome("7", "Möhlmann"),
				new CustomerHome("8", "Janssen")

		));


	}


	@GetMapping
	Iterable<CustomerHome> getCustomers() {
		return customers;
	}

	@GetMapping("/{id}")
	Optional<CustomerHome> getCustomerHomeById(@PathVariable String id) {
		for (CustomerHome c: customers) {
			if (c.getId().equals(id)) {
				return Optional.of(c);
			}
		}
		return Optional.empty();
	}

	@PostMapping("/customers/add")
	public CustomerHome postCustomerHome(@RequestBody CustomerHome  newCustomer) {
		return postCustomerHome(newCustomer);
	}

	@PutMapping("/{id}")
	ResponseEntity<CustomerHome> putCustomerHome(@PathVariable String id, @RequestBody CustomerHome customerHome) {
		int customerHomeIndex = -1;

		for (CustomerHome c: customers) {
			if(c.getId().equals(id)) {
				customerHomeIndex = customers.indexOf(c);
				customers.set(customerHomeIndex, customerHome);

			}
		}

		return (customerHomeIndex == -1) ?
				new ResponseEntity<>(postCustomerHome(customerHome), HttpStatus.CREATED) :
				new ResponseEntity<>(customerHome, HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	void deleteCustomerHome(@PathVariable String id) {
		customers.removeIf(c -> c.getId().equals(id));
	}
}
