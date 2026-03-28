package com.example.ecommerce_spring.repositories;
import com.example.ecommerce_spring.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long>{
}
