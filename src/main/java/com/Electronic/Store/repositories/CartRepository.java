package com.Electronic.Store.repositories;

import com.Electronic.Store.entities.Cart;
import com.Electronic.Store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CartRepository extends JpaRepository<Cart,String> {
    Optional<Cart>findByUser(User user);
}
