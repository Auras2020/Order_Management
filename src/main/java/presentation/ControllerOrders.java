package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Orders;
import model.Product;
import start.ReflectionExample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

public class ControllerOrders {

    public ViewOrders viewOrders;
    public ViewProducts viewProducts;
    private OrderBLL orderBll = new OrderBLL();
    private ClientBLL clientBll = new ClientBLL();
    private ProductBLL productBll = new ProductBLL();
    protected static final Logger LOGGER = Logger.getLogger(ControllerOrders.class.getName());

    public ControllerOrders(ViewOrders viewOrders, ViewProducts viewProducts){
        this.viewOrders=viewOrders;
        this.viewProducts=viewProducts;

        this.viewOrders.addInsertOrderActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int flag=0;
                int id=0;
                try{
                    id=Integer.parseInt(viewOrders.getText1());
                } catch(NumberFormatException ex){
                    flag=1;
                    viewOrders.setText1("Id is a number!!");
                }
                int id_client=0;
                try{
                    id_client=Integer.parseInt(viewOrders.getText2());
                }
                catch(NumberFormatException ex){
                    flag=1;
                    viewOrders.setText2("Id_client is a number!!");
                }
                int id_product=0;
                try{
                    id_product=Integer.parseInt(viewOrders.getText3());
                }
                catch(NumberFormatException ex){
                    flag=1;
                    viewOrders.setText3("Id_product is a number!!");
                }
                int quantity=0;
                try{
                    quantity= Integer.parseInt(viewOrders.getText4());
                }
                catch(NumberFormatException ex){
                    flag=1;
                    viewOrders.setText4("Quantity is a number!!");
                }

                if(flag==0){
                    Orders order=new Orders(id, id_client, id_product, quantity);
                    Orders order1=null;
                    List<Client> clienti=clientBll.findAll();
                    List<Product> products=productBll.findAll();
                    int flag1=1;
                    Product product=null;
                    Client client=null;

                    try{
                        client=clientBll.findClient("id", id_client + "");
                    } catch (Exception ex) {
                        flag1=0;
                        viewOrders.setMessage("Client with id=" + id_client + " does not exist!!");
                    }
                    try{
                        product=productBll.findProduct("id", id_product + "");
                    } catch (Exception ex) {
                        flag1=0;
                        viewOrders.setMessage("Product with id=" + id_product + " does not exist!!");
                    }

                    if(flag1==1){
                        System.out.println("The following client was selected:\n");
                        ReflectionExample.retrieveProperties(client);
                        System.out.println();
                        System.out.println("The following product was selected:\n");
                        ReflectionExample.retrieveProperties(product);
                        System.out.println();

                        if(quantity>product.getQuantity()){
                            viewOrders.setMessage("There are not enough products in stock!!");
                        }
                        else{
                            viewOrders.setMessage("");
                            try{
                                order1=orderBll.insert(order);
                            } catch (Exception ex) {
                                LOGGER.log(Level.INFO, ex.getMessage());
                            }

                            try{
                                Document document=new Document();
                                PdfWriter.getInstance(document, new FileOutputStream("Orders.pdf"));
                                document.open();
                                Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                                for(Orders o: orderBll.findAll()){
                                    Chunk chunk1=new Chunk("Clientul " + clientBll.findClient("id", o.getId_client() + "").getName()
                                            + " a cumparat " + o.getQuantity()  + " x " + productBll.findProduct("id", o.getId_product() + "").getName(), font);
                                    Paragraph p1=new Paragraph(chunk1);

                                    Chunk chunk2=new Chunk(" care au costat " + productBll.findProduct("id", o.getId_product() + "").getPrice()
                                            + " lei/bucata, in total " + productBll.findProduct("id", o.getId_product() + "").getPrice()*o.getQuantity() + " lei.", font);
                                    Paragraph p2=new Paragraph(chunk2);

                                    Chunk chunk3=new Chunk("", font);
                                    Paragraph p3=new Paragraph(chunk3);

                                    document.add(p1);
                                    document.add(p2);
                                    document.add(p3);
                                }
                                document.close();
                            } catch (FileNotFoundException ex){
                                ex.printStackTrace();
                            } catch (DocumentException ex) {
                                ex.printStackTrace();
                            }

                            List<Product> products1=null;
                            try{
                                if(product.getQuantity()==quantity){
                                    products1=productBll.delete(products, "id", id_product + "");
                                    System.out.println("deleted products:\n ");
                                    for(Product prod: products1){
                                        ReflectionExample.retrieveProperties(prod);
                                        System.out.println();
                                    }
                                }
                                else {
                                    products1 = productBll.update(products, "quantity", "id", product.getQuantity() - quantity + "", id_product + "");
                                    System.out.println("updated products:\n ");
                                    for(Product prod: products1){
                                        ReflectionExample.retrieveProperties(prod);
                                        System.out.println();
                                    }
                                }
                            } catch (Exception ex) {
                                LOGGER.log(Level.INFO, ex.getMessage());
                            }

                            refreshProductsTable();
                            refreshOrdersTable();

                            System.out.println("inserted order:\n ");
                            ReflectionExample.retrieveProperties(order1);
                            System.out.println();
                        }
                    }
                }
            }
        });
    }

    public void refreshProductsTable(){
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

    public void refreshOrdersTable(){
        viewOrders.getModel().setRowCount(0);
        List<Orders> list=orderBll.findAll();

        for(Orders o: list){
            Object[] row=new Object[4];
            row[0]=o.getId();
            row[1]=o.getId_client();
            row[2]=o.getId_product();
            row[3]=o.getQuantity();
            viewOrders.getModel().addRow(row);
        }
    }
}
