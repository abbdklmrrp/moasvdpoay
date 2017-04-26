/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.nut.dao;

import java.util.List;

import nc.nut.entity.Product;

/**
 * @author Alistratenko Nikita
 */
public interface ProductDAO extends Dao<Product> {
    List<Product> getByTypeId(int id);

    List<Product> getByCategoryId(int id);

    List<Product> getByProcessingStatus(int id);

    boolean changeDuration(int productId, int duration_in_days);

    boolean changeDescription(int productId, String description);

    List<Product> getServicesNotInTariff(int tarrifId);

    List<Product> getSerivicesByPlace(int placeId);

    List<Product> getProductsByPlace(int placeId, int typeId);

}
