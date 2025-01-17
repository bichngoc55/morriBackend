package com.jelwery.morri.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jelwery.morri.Model.Cart;
import com.jelwery.morri.Model.CartItem;
import com.jelwery.morri.Repository.CartItemRepository;
import com.jelwery.morri.Repository.CartRepository;
import com.jelwery.morri.Repository.CustomerRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart getCartByUserId(String userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(customerRepository.findById(userId).orElseThrow());
            cart.setItems(new ArrayList<>());
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    public Cart addItemToCart(String userId, CartItem item) {
        Cart cart = getCartByUserId(userId);
        CartItem savedItem = cartItemRepository.save(item);
        cart.getItems().add(savedItem);
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(String userId, String itemId) {
        Cart cart = getCartByUserId(userId);
        cart.setItems(cart.getItems().stream()
            .filter(item -> !item.getId().equals(itemId))
            .collect(Collectors.toList()));
        cartItemRepository.deleteById(itemId);
        return cartRepository.save(cart);
    }

    public Cart updateItemQuantity(String userId, String itemId, Integer quantity) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().stream()
            .filter(item -> item.getId().equals(itemId))
            .findFirst()
            .ifPresent(item -> item.setSelectedQuantity(quantity));
        return cartRepository.save(cart);
    }

    public void clearCart(String userId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().forEach(item -> cartItemRepository.deleteById(item.getId()));
        cart.setItems(new ArrayList<>());
        cartRepository.save(cart);
    }
}
