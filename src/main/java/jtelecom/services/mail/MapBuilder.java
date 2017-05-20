package jtelecom.services.mail;

import jtelecom.dao.complaint.Complaint;
import jtelecom.dao.product.Product;
import jtelecom.dao.user.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Moiseienko Petro
 * @since 20.05.2017.
 */
public class MapBuilder {
    Map<String, Object> model;
    User user;
    Product product;

    public MapBuilder(User user) {
        model = new HashMap<>();
        this.user = user;
    }

    public MapBuilder(User user, Product product) {
        model = new HashMap<>();
        this.user = user;
        this.product = product;
    }

    public MapBuilder setUserName() {
        model.put("name", user.getName());
        return this;
    }

    public MapBuilder setUserSurname() {
        model.put("surname", user.getSurname());
        return this;
    }

    public MapBuilder setUserPassword() {
        model.put("password", user.getPassword());
        return this;
    }

    public MapBuilder setProductName() {
        model.put("productName", product.getName());
        return this;
    }

    public MapBuilder setProductDescription() {
        model.put("description", product.getDescription());
        return this;
    }

    public MapBuilder setProductType() {
        model.put("productType", product.getProductType().getName());
        return this;
    }

    public MapBuilder setComplaintId(int complaintId) {
        model.put("id", complaintId);
        return this;
    }
    public MapBuilder setBeginDate(Calendar beginDate){
        model.put("start",new SimpleDateFormat("dd.MM.yyyy").format(beginDate.getTime()));
        return this;
    }
    public MapBuilder setEndDate(Calendar endDate){
        model.put("end",new SimpleDateFormat("dd.MM.yyyy").format(endDate.getTime()));
        return this;
    }

    public Map<String, Object> build() {
        return model;
    }
}
