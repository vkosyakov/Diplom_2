package org.example.order.listOrders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ListOrders {

    private boolean success;
    private List<Orders> orders;
    private int total;
    private int totalToday;



}
