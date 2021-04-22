package bll;

import dao.OrderDAO;
import model.Orders;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {

    private OrderDAO orderDAO;

    public OrderBLL(){
        orderDAO=new OrderDAO();
    }

    public List<Orders> findAll() {
        List<Orders> st=new ArrayList<>();
        st.addAll(orderDAO.findAll());
        if (st == null) {
            throw new NoSuchElementException("Orders were not found!");
        }
        return st;
    }

    public Orders insert(Orders orders) {
        Orders st = orderDAO.insert(orders);
        if (st == null) {
            throw new NoSuchElementException("The order could not be created!");
        }
        return st;
    }
}
