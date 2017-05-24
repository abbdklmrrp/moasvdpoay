package jtelecom.services.mail;

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
class MapBuilder {
    private Map<String, Object> model;
    private User user;
    private Product product;

    MapBuilder(User user) {
        model = new HashMap<>();
        this.user = user;
    }

    MapBuilder(User user, Product product) {
        model = new HashMap<>();
        this.user = user;
        this.product = product;
    }

    MapBuilder setUserName() {
        model.put("name", user.getName());
        return this;
    }

    MapBuilder setUserSurname() {
        model.put("surname", user.getSurname());
        return this;
    }

    MapBuilder setUserPassword() {
        model.put("password", user.getPassword());
        return this;
    }

    MapBuilder setProductName() {
        model.put("productName", product.getName());
        return this;
    }

    MapBuilder setProductDescription() {
        model.put("description", product.getDescription());
        return this;
    }

    MapBuilder setProductType() {
        model.put("productType", product.getProductType().getName());
        return this;
    }

    MapBuilder setComplaintId(int complaintId) {
        model.put("id", complaintId);
        return this;
    }

    MapBuilder setBeginDate(Calendar beginDate) {
        model.put("start", new SimpleDateFormat("dd.MM.yyyy").format(beginDate.getTime()));
        return this;
    }

    MapBuilder setEndDate(Calendar endDate) {
        model.put("end", new SimpleDateFormat("dd.MM.yyyy").format(endDate.getTime()));
        return this;
    }

    Map<String, Object> build() {
        return model;
    }
}
