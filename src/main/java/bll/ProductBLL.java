package bll;

import dao.ProductDAO;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {

    private ProductDAO productDAO;

    public ProductBLL() {
        productDAO=new ProductDAO();
    }

    public List<Product> findAll() {
        List<Product> st=new ArrayList<>();
        st.addAll(productDAO.findAll());
        if (st == null) {
            throw new NoSuchElementException("Products were not found!");
        }
        return st;
    }

    public Product findProduct(String field, String value) {
        Product st = productDAO.find(field, value);
        if (st == null) {
            throw new NoSuchElementException("The product was not found!");
        }
        return st;
    }

    public Product insert(Product product) {
        Product st = productDAO.insert(product);
        if (st == null) {
            throw new NoSuchElementException("The product could not be inserted!");
        }
        return st;
    }

    public List<Product> update(List<Product> list1, String field1, String field2, String value1, String value2) {
        List<Product> st=productDAO.update(list1, field1, field2, value1, value2);
        if (st == null) {
            throw new NoSuchElementException("The client could not be updated!");
        }
        return st;
    }

    public List<Product> delete(List<Product> list1, String field, String value) {
        List<Product> st = productDAO.delete(list1, field, value);
        if (st == null) {
            throw new NoSuchElementException("The client could not be deleted!");
        }
        return st;
    }
}
