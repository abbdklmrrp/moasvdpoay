package nc.nut.services;

import nc.nut.dao.price.Price;
import nc.nut.dao.price.PriceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Anna Rysakova on 10.05.2017.
 */
@Component
public class PriceService {

    @Resource
    private PriceDao priceDao;
    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    public void fillPriceOfProductByRegion(Integer productId, Integer[] placeId, BigDecimal[] price) {
        ArrayList<Price> priceArrayList = fillInDTOForBatchUpdate(productId, placeId, price);
        priceDao.fillPriceOfProductByRegion(priceArrayList);

    }

    private ArrayList<Price> fillInDTOForBatchUpdate(Integer productId, Integer[] placeId, BigDecimal[] priceOfProduct) {
        ArrayList<Price> priceByRegionDtos = new ArrayList<>();

        for (int i = 0, j = 0; i < placeId.length; i++, j++) {
            Price price = new Price();
            price.setProductId(productId);
            price.setPlaceId(placeId[i]);
            price.setPrice(priceOfProduct[i+1]);
            priceByRegionDtos.add(price);
        }
        return priceByRegionDtos;
    }
}
