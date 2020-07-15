package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
