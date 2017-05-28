package jtelecom.services.price;

import jtelecom.dao.price.Price;
import jtelecom.dao.price.PriceDAO;
import jtelecom.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anna Rysakova
 */
@Component
public class PriceServiceImpl implements PriceService {

    private static Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);

    @Resource
    private PriceDAO priceDAO;

    /**
     * {@inheritDoc}
     */
    public List<Price> fillInListWithProductPriceByRegion(Integer productId, Integer[] placeId, BigDecimal[] priceByRegion) {
        ArrayList<Price> listPriceByRegion = new ArrayList<>();
        for (int i = 0; i < priceByRegion.length; i++) {
            if (placeId[i] != 0 & priceByRegion[i].compareTo(BigDecimal.ZERO) > 0) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(Integer[] placeId, BigDecimal[] priceByRegion) {
        return (placeId.length == 0 & priceByRegion.length == 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateProductPriceInRegions(Integer productId, Integer[] placeId, BigDecimal[] priceByRegion) {
        List<Price> oldPriceInRegionsList = priceDAO.getPriceInRegionInfoByProduct(productId);
        logger.debug("Old prices in regions by product ID {}", oldPriceInRegionsList.toString());

        List<Price> newPriceInRegionsList = fillInListWithProductPriceByRegion(productId, placeId, priceByRegion);
        logger.debug("New prices in regions by product ID {}", newPriceInRegionsList.toString());

        List<Price> uniqueServicesInFirstCollection = (List<Price>) CollectionUtil
                .firstCollectionMinusSecondCollection(oldPriceInRegionsList, newPriceInRegionsList);
        logger.debug("Unique elements in oldPriceInRegionsList {}", uniqueServicesInFirstCollection.toString());
        priceDAO.deleteProductPriceInRegion(uniqueServicesInFirstCollection);

        uniqueServicesInFirstCollection = (List<Price>) CollectionUtil
                .firstCollectionMinusSecondCollection(newPriceInRegionsList, oldPriceInRegionsList);
        logger.debug("Unique elements in newPriceInRegionsList {}", uniqueServicesInFirstCollection.toString());
        priceDAO.fillPriceOfProductByRegion(uniqueServicesInFirstCollection);
    }
}
