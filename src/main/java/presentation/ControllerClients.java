package presentation;

import bll.ClientBLL;
import model.Client;
import start.ReflectionExample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ControllerClients {

    private ViewClients viewClients;
    private ClientBLL clientBll = new ClientBLL();
    protected static final Logger LOGGER = Logger.getLogger(ControllerClients.class.getName());

    public ControllerClients(ViewClients viewClients){
        this.viewClients = viewClients;

        this.viewClients.addInsertClientActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int flag=0;
                int id=0;
                try{
                    id=Integer.parseInt(viewClients.getText1());
                }
                catch(NumberFormatException ex){
                    flag=1;
                    viewClients.setText1("Id is a number!!");
                }
                String name= viewClients.getText2();
                String address= viewClients.getText3();
                String email= viewClients.getText4();
                int age=0;
                try{
                    age=Integer.parseInt(viewClients.getText5());
                }
                catch(NumberFormatException ex){
                    flag=1;
                    viewClients.setText5("Age is a number!!");
                }

                if(flag==0){
                    Client client=new Client(id, name, address, email, age);
                    Client client1=null;
                    try{
                        client1=clientBll.insert(client);
                    }
                    catch (Exception ex) {
                        LOGGER.log(Level.INFO, ex.getMessage());
                    }
                    System.out.println("inserted client:\n ");
                    ReflectionExample.retrieveProperties(client1);
                    System.out.println();

                    refreshTable();
                }
            }
        });

        this.viewClients.addUpdateClientActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String updatedField= viewClients.getTextUpdate1();
                String restrictionField= viewClients.getTextUpdate2();
                String updatedValue= viewClients.getTextUpdate3();
                String restrictionValue= viewClients.getTextUpdate4();

                List<Client> clienti=null;
                try{
                    List<Client> list1=clientBll.findAll();
                    clienti=clientBll.update(list1, updatedField, restrictionField, updatedValue, restrictionValue);
                } catch (Exception ex) {
                    LOGGER.log(Level.INFO, ex.getMessage());
                }

                refreshTable();

                if(clienti!=null){
                    System.out.println("updated clients:\n ");
                    for(Client client: clienti){
                        ReflectionExample.retrieveProperties(client);
                        System.out.println();
                    }
                }
            }
        });

        this.viewClients.addDeleteClientActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textDelete1= viewClients.getTextDelete1();
                String textDelete2= viewClients.getTextDelete2();

                List<Client> clienti=null;
                try{
                    List<Client> list1=clientBll.findAll();
                    clienti=clientBll.delete(list1, textDelete1, textDelete2);

                } catch (Exception ex) {
                    LOGGER.log(Level.INFO, ex.getMessage());
                }

                refreshTable();

                System.out.println("deleted clients:\n ");
                for(Client client: clienti){
                    ReflectionExample.retrieveProperties(client);
                    System.out.println();
                }
            }
        });

        this.viewClients.addViewClientsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Client> clienti=clientBll.findAll();
                System.out.println("view all clients:\n ");
                for(Client client: clienti){
                    ReflectionExample.retrieveProperties(client);
                    System.out.println();
                }
            }
        });
    }

    public void refreshTable(){
        viewClients.getModel().setRowCount(0);
        List<Client> list=clientBll.findAll();

        for(Client c: list){
            Object[] row=new Object[5];
            row[0]=c.getId();
            row[1]=c.getName();
            row[2]=c.getAddress();
            row[3]=c.getEmail();
            row[4]=c.getAge();
            viewClients.getModel().addRow(row);
        }
    }

}
