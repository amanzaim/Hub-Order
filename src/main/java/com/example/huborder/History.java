package com.example.huborder;

public class History {
    private String restaurantOrdered;
    private String orderItem;
    private String price;

    public History(String text1, String text2, String totalprice) {
        restaurantOrdered = text1;
        orderItem = text2;
        price = totalprice;
    }

    public String getRestaurantOrdered() {
        return restaurantOrdered;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public String getPrice() {
        return price;
    }

}
