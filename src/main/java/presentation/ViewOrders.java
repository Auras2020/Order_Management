package presentation;

import bll.OrderBLL;
import model.Orders;

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

public class ViewOrders extends JFrame{

    private JFrame frame3;

    //components of the third frame
    private JLabel title3;

    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;

    private JTextField text1;
    private JTextField text2;
    private JTextField text3;
    private JTextField text4;

    private JTextField message;

    private JButton insertOrder;

    private JTable table3;
    private JScrollPane pane3;

    protected static final Logger LOGGER = Logger.getLogger(ViewOrders.class.getName());
    private DefaultTableModel model;
    private List<Orders> orders=new ArrayList<>();

    public ViewOrders(){
        frame3=new JFrame("Orders");
        frame3.setBackground(Color.white);
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        title3=new JLabel("Orders");

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        panel1.add(title3);

        label1=new JLabel("id: ");
        label2=new JLabel("client: ");
        label3=new JLabel("product: ");
        label4=new JLabel("quantity: ");
        text1=new JTextField();
        text2=new JTextField();
        text3=new JTextField();
        text4=new JTextField();

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2, 4, 10, 10));
        panel2.add(label1);
        panel2.add(label2);
        panel2.add(label3);
        panel2.add(label4);
        panel2.add(text1);
        panel2.add(text2);
        panel2.add(text3);
        panel2.add(text4);

        message=new JTextField();

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel3.add( Box.createRigidArea(new Dimension(50,0)) );
        panel3.add(message);
        panel3.add( Box.createRigidArea(new Dimension(50,0)) );

        insertOrder=new JButton("INSERT");

        JPanel panel3_1 = new JPanel();
        panel3_1.setLayout(new BoxLayout(panel3_1, BoxLayout.X_AXIS));
        panel3_1.add(insertOrder);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
        panel4.add( Box.createRigidArea(new Dimension(0,20)) );
        panel4.add(panel1);
        panel4.add( Box.createRigidArea(new Dimension(0,20)) );
        panel4.add(panel2);
        panel4.add( Box.createRigidArea(new Dimension(0,20)) );
        panel4.add(panel3_1);
        panel4.add( Box.createRigidArea(new Dimension(0,20)) );
        panel4.add(panel3);
        panel4.add( Box.createRigidArea(new Dimension(0,20)) );

        try{
            table3=retrieveProperties(returnList());
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        pane3=new JScrollPane(table3);

        JPanel panel5 = new JPanel();
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.X_AXIS));
        panel5.add(pane3);

        JPanel panel6 = new JPanel();
        panel6.setLayout(new BoxLayout(panel6, BoxLayout.Y_AXIS));
        panel6.add(panel4);
        panel6.add( Box.createRigidArea(new Dimension(0,30)) );
        panel6.add(panel5);

        frame3.add(panel6);
        frame3.setVisible(true);
        frame3.pack();
    }

    public List<Object> returnList() throws SQLException {
        OrderBLL orderBLL=new OrderBLL();
        try {
            orders.addAll(orderBLL.findAll());
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
        }
        List<Object> objects=new ArrayList<>(orders);
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

    public void addInsertOrderActionListener(ActionListener insertBut){
        this.insertOrder.addActionListener(insertBut);
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

    public void setText2(String text2) {
        this.text2.setText(text2);
    }

    public void setText3(String text3) {
        this.text3.setText(text3);
    }

    public void setText4(String text4) {
        this.text4.setText(text4);
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }
}
