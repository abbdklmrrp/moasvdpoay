/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao;

import java.util.List;
import nc.nut.entity.Product;

/**
 *
 * @author Alistratenko Nikita
 */
public interface ProductDAO {

    Product save(Product product);

    boolean delete(Product product);

    Product getById(int id);

    List<Product> getByTypeId(int id);

    List<Product> getByCategoryId(int id);

    List<Product> getByProcessingStatus(int id);

    boolean changeDuration(int productId, int duration_in_days);

    boolean changeDescription(int productId, String description);

}
