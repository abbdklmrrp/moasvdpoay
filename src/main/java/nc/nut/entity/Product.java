/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.entity;

/**
 *
 * @author Alistratenko Nikita
 */
public class Product {

    private int id;
    private int category_id;
    private int duration_in_days;
    private int type_id;
    private int need_processing;
    private String name;
    private String description;
    private int status;

    public Product() {
    }

    public Product(int id, int category_id, int duration_in_days, int type_id, int need_processing, String name, String description, int status) {
        this.id = id;
        this.category_id = category_id;
        this.duration_in_days = duration_in_days;
        this.type_id = type_id;
        this.need_processing = need_processing;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getDuration_in_days() {
        return duration_in_days;
    }

    public void setDuration_in_days(int duration_in_days) {
        this.duration_in_days = duration_in_days;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getNeed_processing() {
        return need_processing;
    }

    public void setNeed_processing(int need_processing) {
        this.need_processing = need_processing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id
                + ", category_id=" + category_id
                + ", duration_in_days=" + duration_in_days
                + ", type_id=" + type_id
                + ", need_processing=" + need_processing
                + ", name=" + name
                + ", description=" + description
                + ", status=" + status + '}';
    }

}
