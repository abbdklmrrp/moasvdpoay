package jtelecom.dao.price;

import jtelecom.dao.interfaces.DAO;
import jtelecom.dto.PriceByRegionDTO;

import java.util.List;


/**
 * Created by Yuliya Pedash on 27.04.2017.
 */
public interface PriceDAO extends DAO<Price> {
    /**
     * This method returns <code>Price</code>  object by product id and place id.
     * For more details about <code>Price</code>  object: {@link Price}
     *
     * @param productId id of product
     * @param placeId   id of place
     * @return <code>Price</code> object
     * @see Price
     */
    Price getPriceByProductIdAndPlaceId(Integer productId, Integer placeId);

    boolean fillPriceOfProductByRegion(List<Price> priceByRegionDtos);

    List<PriceByRegionDTO> getPriceInRegionsByProduct(int productId);

    List<PriceByRegionDTO> getAllRegionsAndProductPriceInRegionByProductId(Integer productId);

    List<Price> getPriceInRegionInfoByProduct(int productId);

    boolean deleteProductPriceInRegion(List<Price> priceInRegion);

    List<PriceByRegionDTO> getLimitedQuantityProductPricesInRegions(int prouctId, int start, int length, String sort, String search);

    Integer getCountPriceByPlace(String search, Integer productId);
}
