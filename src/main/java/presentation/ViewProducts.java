package presentation;

import bll.ProductBLL;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewProducts extends JFrame {

    private JFrame frame2;

    //components of the second frame
    private JLabel title2;

    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;

    private JTextField text1;
    private JTextField text2;
    private JTextField text3;
    private JTextField text4;

    private JLabel update1;
    private JLabel update2;
    private JLabel update3;
    private JLabel update4;

    private JTextField textUpdate1;
    private JTextField textUpdate2;
    private JTextField textUpdate3;
    private JTextField textUpdate4;

    private JLabel delete1;
    private JLabel delete2;

    private JTextField textDelete1;
    private JTextField textDelete2;

    private JTable table2;
    private JScrollPane pane2;

    private JButton insertProduct;
    private JButton updateProduct;
    private JButton deleteProduct;
    private JButton viewAllProducts;

    protected static final Logger LOGGER = Logger.getLogger(ViewProducts.class.getName());
    private DefaultTableModel model;
    private List<Product> products=new ArrayList<>();

    public ViewProducts(){
        frame2=new JFrame("Products");
        frame2.setBackground(Color.white);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        title2=new JLabel("Products");

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        panel1.add(title2);

        label1=new JLabel("id: ");
        label2=new JLabel("name: ");
        label3=new JLabel("quantity: ");
        label4=new JLabel("price: ");
        text1=new JTextField();
        text2=new JTextField();
        text3=new JTextField();
        text4=new JTextField();

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(4, 2, 10, 10));
        panel2.add(label1);
        panel2.add(text1);
        panel2.add(label2);
        panel2.add(text2);
        panel2.add(label3);
        panel2.add(text3);
        panel2.add(label4);
        panel2.add(text4);

        insertProduct=new JButton("INSERT");

        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel3.add(insertProduct);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
        panel4.add(panel2);
        panel4.add(panel3);

        update1=new JLabel("Field to be updated: ");
        update2=new JLabel("Field restriction: ");
        update3=new JLabel("Value to be updated: ");
        update4=new JLabel("Value restriction: ");
        textUpdate1=new JTextField();
        textUpdate2=new JTextField();
        textUpdate3=new JTextField();
        textUpdate4=new JTextField();

        JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayout(4, 2, 10, 10));
        panel5.add(update1);
        panel5.add(textUpdate1);
        panel5.add(update2);
        panel5.add(textUpdate2);
        panel5.add(update3);
        panel5.add(textUpdate3);
        panel5.add(update4);
        panel5.add(textUpdate4);

        updateProduct=new JButton("UPDATE");

        JPanel panel6 = new JPanel();
        panel6.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel6.add(updateProduct);

        JPanel panel7 = new JPanel();
        panel7.setLayout(new BoxLayout(panel7, BoxLayout.Y_AXIS));
        panel7.add(panel5);
        panel7.add(panel6);

        delete1=new JLabel("Field to be deleted: ");
        delete2=new JLabel("Value to be deleted: ");
        textDelete1=new JTextField();
        textDelete2=new JTextField();

        JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayout(2, 2, 10, 10));
        panel8.add(delete1);
        panel8.add(textDelete1);
        panel8.add(delete2);
        panel8.add(textDelete2);

        deleteProduct=new JButton("DELETE");

        JPanel panel9 = new JPanel();
        panel9.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel9.add(deleteProduct);

        JPanel panel10 = new JPanel();
        panel10.setLayout(new BoxLayout(panel10, BoxLayout.Y_AXIS));
        panel10.add(panel8);
        panel10.add(panel9);

        viewAllProducts=new JButton("SELECT ALL");

        JPanel panel11 = new JPanel();
        panel11.setLayout(new BoxLayout(panel11, BoxLayout.X_AXIS));
        panel11.add(viewAllProducts);

        JPanel panel12 = new JPanel();
        panel12.setLayout(new BoxLayout(panel12, BoxLayout.X_AXIS));
        panel12.add( Box.createRigidArea(new Dimension(20,0)) );
        panel12.add(panel4);
        panel12.add( Box.createRigidArea(new Dimension(20,0)) );
        panel12.add(panel7);
        panel12.add( Box.createRigidArea(new Dimension(20,0)) );
        panel12.add(panel10);
        panel12.add( Box.createRigidArea(new Dimension(20,0)) );
        panel12.add(panel11);
        panel12.add( Box.createRigidArea(new Dimension(20,0)) );

        JPanel panel13 = new JPanel();
        panel13.setLayout(new BoxLayout(panel13, BoxLayout.Y_AXIS));
        panel13.add(panel1);
        panel13.add( Box.createRigidArea(new Dimension(0,20)) );
        panel13.add(panel12);

        try{
            table2=retrieveProperties(returnList());
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        pane2=new JScrollPane(table2);

        JPanel panel14 = new JPanel();
        panel14.setLayout(new BoxLayout(panel14, BoxLayout.X_AXIS));
        panel14.add(pane2);

        JPanel panel15 = new JPanel();
        panel15.setLayout(new BoxLayout(panel15, BoxLayout.Y_AXIS));
        panel15.add(panel13);
        panel15.add( Box.createRigidArea(new Dimension(0,30)) );
        panel15.add(panel14);

        frame2.add(panel15);
        frame2.setVisible(true);
        frame2.pack();
    }

    public List<Object> returnList() throws SQLException {
        ProductBLL productBLL=new ProductBLL();
        try {
            products.addAll(productBLL.findAll());
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
        }
        List<Object> objects=new ArrayList<>(products);
        return objects;
    }

    public JTable retrieveProperties(List<Object> list){
        int lin=0;
        String []column=null;
        String [][]data=null;
        for(Object object: list){
            int i=0;
            if(lin==0){
                column=new String[object.getClass().getDeclaredFields().length];
                data=new String[list.size()][object.getClass().getDeclaredFields().length];
            }
            for (Field field : object.getClass().getDeclaredFields()){
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(object);
                    column[i]=field.getName();
                    data[lin][i]=value + "";
                    i++;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            lin++;
        }

        JTable table=new JTable();
        model=new DefaultTableModel(data, column);
        model.setColumnIdentifiers(column);
        table.setModel(model);
        return table;
    }

    public void addInsertProductActionListener(ActionListener insertBut){
        this.insertProduct.addActionListener(insertBut);
    }

    public void addUpdateProductActionListener(ActionListener updateBut){
        this.updateProduct.addActionListener(updateBut);
    }

    public void addDeleteProductActionListener(ActionListener deleteBut){
        this.deleteProduct.addActionListener(deleteBut);
    }

    public void addViewProductsActionListener(ActionListener viewBut){
        this.viewAllProducts.addActionListener(viewBut);
    }

    public String getText1() {
        return text1.getText();
    }

    public String getText2() {
        return text2.getText();
    }

    public String  getText3() {
        return text3.getText();
    }

    public String getText4() {
        return text4.getText();
    }

    public void setText1(String text1) {
        this.text1.setText(text1);
    }

    public void setText3(String text3) {
        this.text3.setText(text3);
    }

    public void setText4(String text4) {
        this.text4.setText(text4);
    }

    public JTable getTable2() {
        return table2;
    }

    public void setTable2(JTable table2) {
        this.table2 = table2;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public String getTextUpdate1() {
        return textUpdate1.getText();
    }

    public String getTextUpdate2() {
        return textUpdate2.getText();
    }

    public String getTextUpdate3() {
        return textUpdate3.getText();
    }

    public String getTextUpdate4() {
        return textUpdate4.getText();
    }

    public String getTextDelete1() {
        return textDelete1.getText();
    }

    public String getTextDelete2() {
        return textDelete2.getText();
    }

    public List<Product> getProducts() {
        return products;
    }
}
