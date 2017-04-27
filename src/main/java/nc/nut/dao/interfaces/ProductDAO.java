/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao.interfaces;


import nc.nut.dao.entity.Product;

import java.util.List;

/**
 * @author Alistratenko Nikita
 */
public interface ProductDAO {

    boolean save(Product product);

    boolean delete(Product product);

    Product getById(int id);

    List<Product> getByTypeId(int id);

    List<Product> getByCategoryId(int id);

    List<Product> getByProcessingStatus(int id);

    boolean changeDuration(int productId, int duration_in_days);

    boolean changeDescription(int productId, String description);

}
