/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao.entity;

/**
 * @author Alistratenko Nikita
 */
public class Customer {

    private int id;
    private String name;
    private int invoice;
    private String secret_key;

    public Customer() {
    }

    public Customer(int id, String name, int invoice, String secret_key) {
        this.id = id;
        this.name = name;
        this.invoice = invoice;
        this.secret_key = secret_key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInvoice() {
        return invoice;
    }

    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id
                + ", name=" + name
                + ", invoice=" + invoice
                + ", secret_key=" + secret_key + '}';
    }

}
