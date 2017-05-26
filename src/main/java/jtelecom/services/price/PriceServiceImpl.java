package jtelecom.services.price;

import jtelecom.dao.price.Price;
import jtelecom.dao.price.PriceDAO;
import jtelecom.dao.product.ProductDAO;
import jtelecom.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Anna Rysakova on 10.05.2017.
 */
@Component
public class PriceServiceImpl implements PriceService {

    private static Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);
    @Resource
    private ProductDAO productDAO;
    @Resource
    private PriceDAO priceDAO;

    /**
     * This method fill the <code>ArrayList</code> with the values of the product ID
     * and its prices for the region.
     *
     * @param productId     product id
     * @param placeId       place id
     * @param priceByRegion price of product by <code>placeId</code> region
     * @return <code>ArrayList</code> of product id, place id , price in this region
     */
    public List<Price> fillInListWithProductPriceByRegion(Integer productId, Integer[] placeId, BigDecimal[] priceByRegion) {
        ArrayList<Price> listPriceByRegion = new ArrayList<>();
        logger.debug("Create list of product price by region {} ", listPriceByRegion);
        for (int i = 0; i < priceByRegion.length; i++) {
            if (placeId[i] != null & priceByRegion[i] != null) {
                Price price = new Price();
                price.setProductId(productId);
                price.setPlaceId(placeId[i]);
                price.setPrice(priceByRegion[i]);
                listPriceByRegion.add(price);
                logger.debug("to list was add object Price {} ", price);
            }
        }
        return listPriceByRegion;
    }

    @Override
    public boolean isValid(Integer[] placeId, BigDecimal[] priceByRegion) {
        return (Objects.nonNull(placeId) & Objects.nonNull(priceByRegion));
    }

    @Override
    public void updateProductPriceInRegions(Integer productId, Integer[] placeId, BigDecimal[] priceByRegion) {
        List<Price> oldPriceInfo = priceDAO.getPriceInRegionInfoByProduct(productId);
        List<Price> newPriceInfo = fillInListWithProductPriceByRegion(productId, placeId, priceByRegion);

        List<Price> uniqueServicesInFirstCollection = (List<Price>) CollectionUtil
                .firstCollectionMinusSecondCollection(oldPriceInfo, newPriceInfo);
        priceDAO.deleteProductPriceInRegion(uniqueServicesInFirstCollection);

        uniqueServicesInFirstCollection = (List<Price>) CollectionUtil
                .firstCollectionMinusSecondCollection(newPriceInfo, oldPriceInfo);
        priceDAO.fillPriceOfProductByRegion(uniqueServicesInFirstCollection);
    }
}
