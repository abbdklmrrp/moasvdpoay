/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.entity;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alistratenko Nikita
 */
public class Tariff extends Product {

    private List<Product> products;

    public Tariff() {
    }

    public Tariff(int id, String category, int duration_in_days, String type, int need_processing, String name, String description, int status) {
        super(id, category, duration_in_days, type, need_processing, name, description, status);
        products = new LinkedList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Tarrif{" + "id=" + this.getId()
                + ", category=" + this.getCategory()
                + ", duration_in_days=" + this.getDuration_in_days()
                + ", type=" + this.getType()
                + ", need_processing=" + this.getNeed_processing()
                + ", name=" + this.getName()
                + ", description=" + this.getDescription()
                + ", status=" + this.getStatus()
                + '}';
    }

}
