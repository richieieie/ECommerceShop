/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Map;

/**
 *
 * @author Trung
 */
public class Cart {
    private Map<Product, Integer> cart;
    
    public Cart(Map<Product, Integer> cart) {
        this.cart = cart;
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<Product, Integer> cart) {
        this.cart = cart;
    }
    
    public boolean add(Product p) {
        if (cart.containsKey(p)) {
            cart.put(p, cart.get(p) + 1);
        } else {
            cart.put(p, 1);
        }
        
        return true;
    }
    
    public boolean remove(Product p) {
        if (cart.containsKey(p)) {
            cart.remove(p);
            return true;
        }
        
        return false;
    }
    
    public boolean edit(Product p, int quantity) {
        if (cart.containsKey(p)) {
            cart.put(p, quantity);
            return true;
        }
        
        return false;
    }
}
