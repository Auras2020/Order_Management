package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.ClientAgeValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

public class ClientBLL {

    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
        validators.add(new ClientAgeValidator());

        clientDAO=new ClientDAO();
    }

    public List<Client> findAll() {
        List<Client> st=new ArrayList<>();
        st.addAll(clientDAO.findAll());
        if (st == null) {
            throw new NoSuchElementException("Clients were not found!");
        }
        return st;
    }

    public Client findClient(String field, String value) {
        Client st = clientDAO.find(field, value);
        if (st == null) {
            throw new NoSuchElementException("The client was not found!");
        }
        return st;
    }

    public Client insert(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        Client st = clientDAO.insert(client);
        if (st == null) {
            throw new NoSuchElementException("The client could not be inserted!");
        }
        return st;
    }

    public List<Client> update(List<Client> list1, String field1, String field2, String value1, String value2) {
        List<Client> st=clientDAO.update(list1, field1, field2, value1, value2);
        if (st == null) {
            throw new NoSuchElementException("The client could not be updated!");
        }
        return st;
    }

    public List<Client> delete(List<Client> list1, String field, String value) {
        List<Client> st = clientDAO.delete(list1, field, value);
        if (st == null) {
            throw new NoSuchElementException("The client could not be deleted!");
        }
        return st;
    }
}

