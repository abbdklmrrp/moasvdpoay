/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao.entity;

/**
 * @author Alistratenko Nikita
 */
public class Product {

    private int id;
    private String category;
    private int duration_in_days;
    private String type;
    private int need_processing;
    private String name;
    private String description;
    private int status;

    public Product() {
    }

    public Product(int id, String category, int duration_in_days, String type, int need_processing, String name, String description, int status) {
        this.id = id;
        this.category = category;
        this.duration_in_days = duration_in_days;
        this.type = type;
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

    public String getCategory() {
        return category;
    }

    public void setCategory_id(String category) {
        this.category = category;
    }

    public int getDuration_in_days() {
        return duration_in_days;
    }

    public void setDuration_in_days(int duration_in_days) {
        this.duration_in_days = duration_in_days;
    }

    public String getType() {
        return type;
    }

    public void setType_id(String type) {
        this.type = type;
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
                + ", category=" + category
                + ", duration_in_days=" + duration_in_days
                + ", type=" + type
                + ", need_processing=" + need_processing
                + ", name=" + name
                + ", description=" + description
                + ", status=" + status + '}';
    }

}
