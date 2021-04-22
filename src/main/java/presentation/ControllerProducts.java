package presentation;

import bll.ProductBLL;
import model.Product;
import start.ReflectionExample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerProducts {

    private ViewProducts viewProducts;
    private ProductBLL productBll = new ProductBLL();
    protected static final Logger LOGGER = Logger.getLogger(ControllerProducts.class.getName());

    public ControllerProducts(ViewProducts viewProducts){
        this.viewProducts = viewProducts;

        this.viewProducts.addInsertProductActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int flag=0;
                int id=0;
                try{
                    id=Integer.parseInt(viewProducts.getText1());
                }
                catch(NumberFormatException ex){
                    flag=1;
                    viewProducts.setText1("Id is a number!!");
                }
                String name= viewProducts.getText2();
                int quantity=0;
                try{
                     quantity= Integer.parseInt(viewProducts.getText3());
                }
                catch(NumberFormatException ex){
                    flag=1;
                    viewProducts.setText3("Quantity is a number!!");
                }
                int price=0;
                try{
                    price= Integer.parseInt(viewProducts.getText4());
                }
                catch(NumberFormatException ex){
                    flag=1;
                    viewProducts.setText4("Price is a number!!");
                }

                if(flag==0){
                    Product product=new Product(id, name, quantity, price);
                    Product product1=null;
                    try{
                        product1=productBll.insert(product);
                    }
                    catch (Exception ex) {
                        LOGGER.log(Level.INFO, ex.getMessage());
                    }
                    System.out.println("inserted product:\n ");
                    ReflectionExample.retrieveProperties(product1);
                    System.out.println();

                    refreshTable();
                }
            }
        });

        this.viewProducts.addUpdateProductActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String updatedField= viewProducts.getTextUpdate1();
                String restrictionField= viewProducts.getTextUpdate2();
                String updatedValue= viewProducts.getTextUpdate3();
                String restrictionValue= viewProducts.getTextUpdate4();

                List<Product> products=null;
                try{
                    List<Product> list1=productBll.findAll();
                    products=productBll.update(list1, updatedField, restrictionField, updatedValue, restrictionValue);
                } catch (Exception ex) {
                    LOGGER.log(Level.INFO, ex.getMessage());
                }

                refreshTable();

                System.out.println("updated products:\n ");
                for(Product product: products){
                    ReflectionExample.retrieveProperties(product);
                    System.out.println();
                }

            }
        });

        this.viewProducts.addDeleteProductActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textDelete1= viewProducts.getTextDelete1();
                String textDelete2= viewProducts.getTextDelete2();

                List<Product> products=null;
                try{
                    List<Product> list1=productBll.findAll();
                    products=productBll.delete(list1, textDelete1, textDelete2);

                } catch (Exception ex) {
                    LOGGER.log(Level.INFO, ex.getMessage());
                }

                refreshTable();

                System.out.println("deleted products:\n ");
                for(Product product: products){
                    ReflectionExample.retrieveProperties(product);
                    System.out.println();
                }
            }
        });

        this.viewProducts.addViewProductsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Product> products=productBll.findAll();
                System.out.println("view all products:\n ");
                for(Product product: products){
                    ReflectionExample.retrieveProperties(product);
                    System.out.println();
                }
            }
        });
    }

    public void refreshTable(){
        viewProducts.getModel().setRowCount(0);
        List<Product> list=productBll.findAll();

        for(Product p: list){
            Object[] row=new Object[4];
            row[0]=p.getId();
            row[1]=p.getName();
            row[2]=p.getQuantity();
            row[3]=p.getPrice();
            viewProducts.getModel().addRow(row);
        }
    }
}
