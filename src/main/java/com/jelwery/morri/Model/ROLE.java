package com.jelwery.morri.Model;

public enum ROLE {
    INVENTORY_STAFF,
    SALE_STAFF,
    ADMIN,
    CUSTOMER;
    public static ROLE fromString(String role) {
        if (role != null) {
            try {
                return ROLE.valueOf(role.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Unknown role: " + role);
            }
        }
        return null;
    }
}
