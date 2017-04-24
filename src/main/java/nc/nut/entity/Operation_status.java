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
public class Operation_status {

    private int id;
    private String name;

    public Operation_status() {
    }

    public Operation_status(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Operation_status{" + "id=" + id + ", name=" + name + '}';
    }

}
