package com.example.ecommerce_spring.repositories;

import com.example.ecommerce_spring.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}