package com.Electronic.Store.services.impl;

import com.Electronic.Store.dto.AddItemToCartRequest;
import com.Electronic.Store.dto.CartDto;
import com.Electronic.Store.entities.Cart;
import com.Electronic.Store.entities.CartItem;
import com.Electronic.Store.entities.Product;
import com.Electronic.Store.entities.User;
import com.Electronic.Store.exception.BadApiRequestException;
import com.Electronic.Store.exception.ResourceNotFoundException;
import com.Electronic.Store.repositories.CartItemRepository;
import com.Electronic.Store.repositories.CartRepository;
import com.Electronic.Store.repositories.ProductRepository;
import com.Electronic.Store.repositories.UserRepository;
import com.Electronic.Store.services.CartService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private CartItemRepository cartItemRepository;
    private CartRepository cartRepository;
    private ModelMapper mapper;
    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int qunantity = request.getQuantity();
        String productId = request.getProductId();
        if (qunantity <= 0) {
            throw new BadApiRequestException("Requested quantity is not valid!!");
        }
        //fetching  the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found in database!!"));
        //fetching the user from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found i databases"));

        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }
        //perform cart operations
        //if cart items already present: then update
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        items = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                //item already present in cart
                item.setQuantity(qunantity);
                item.setTotalPrice(qunantity * product.getDiscountedPrice());
            }
            return item;
        }).collect(Collectors.toList());

//        cart.setItems(updatedItems)
        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(qunantity)
                    .totalPrice(qunantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }
            cart.setUser(user);
            Cart updatedCart = cartRepository.save(cart);
            return mapper.map(updatedCart, CartDto.class);
        }



    @Override
    public void removeItemFromCart(String userId, int cartItem) {

        //conditions
        CartItem cartItem1=cartItemRepository.findById(cartItem).orElseThrow(()->new ResourceNotFoundException("Cart Item not found!!"));
        cartItemRepository.delete(cartItem1);

    }

    @Override
    public void clearCart(String userId) {
        //fetch the user from db
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found in database!!"));
        Cart cart=cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart of given user not found!!"));
        cart.getItems().clear();
        cartRepository.save(cart);

    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found in database!!"));
        Cart cart=cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart of given user not found!!"));
        return mapper.map(cart,CartDto.class);
    }
}
