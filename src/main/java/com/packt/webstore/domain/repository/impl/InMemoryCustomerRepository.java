package com.packt.webstore.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository{

	public List<Customer> getAllCustomers() {
		List<Customer> cust = new ArrayList<Customer>();
		//Customer c1 = new Customer(1, 2, "Chitra", "Brecon");
		//cust.add(c1);
		return cust;
	}
	
}
