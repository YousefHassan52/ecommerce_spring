package com.example.ecommerce_spring.repositories;
import com.example.ecommerce_spring.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long>{


    List<Order> findAllByCustomer_Id(Long customerId);
}
