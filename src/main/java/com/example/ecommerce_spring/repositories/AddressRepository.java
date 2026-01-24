package com.example.ecommerce_spring.repositories;

import com.example.ecommerce_spring.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}