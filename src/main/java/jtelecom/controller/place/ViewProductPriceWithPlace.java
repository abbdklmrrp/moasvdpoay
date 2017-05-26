package jtelecom.controller.place;

import jtelecom.dao.place.Place;
import jtelecom.dao.place.PlaceDAO;
import jtelecom.dao.price.PriceDAO;
import jtelecom.dto.PriceByRegionDTO;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Anna Rysakova
 */
@RestController
@RequestMapping({"admin"})
public class ViewProductPriceWithPlace {
    private static Logger logger = LoggerFactory.getLogger(ViewProductPriceWithPlace.class);
    @Resource
    private PlaceDAO placeDAO;
    @Resource
    private PriceDAO priceDAO;

    @RequestMapping(value = {"allPlace"}, method = RequestMethod.GET)
    public ListHolder allPlaces(@ModelAttribute GridRequestDto request) {
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getLength();
        String search = request.getSearch();
        List<Place> data = placeDAO.getLimitedQuantityPlace(start, length, sort, search);
        int size = placeDAO.getCountPlacesWithSearch(search);
        return ListHolder.create(data, size);
    }

    @RequestMapping(value = {"viewPriceByPlace"}, method = RequestMethod.GET)
    public ModelAndView getDetailsPriceByRegion(@RequestParam(value = "id") Integer id, ModelAndView mav) {
        mav.addObject("id", id);
        String placeName = placeDAO.getPlaceNameById(id);
        mav.addObject("placeName", placeName);
        mav.setViewName("newPages/admin/viewProductsPriceByRegion");
        return mav;
    }

    @RequestMapping(value = {"getDetailsPriceByPlace/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public ListHolder allPricesByPlace(@ModelAttribute GridRequestDto request,
                                       @PathVariable(value = "id") Integer placeId) {
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getLength();
        String search = request.getSearch();
        List<PriceByRegionDTO> data = placeDAO.getLimitedQuantityPriceByPlace(placeId, start, length, sort, search);
        int size = placeDAO.getCountPriceByPlace(search, placeId);
        return ListHolder.create(data, size);
    }

    @RequestMapping(value = {"productsPriceInRegions/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public ListHolder productsPriceInRegions(@ModelAttribute GridRequestDto request,
                                             @PathVariable(value = "id") Integer id) {
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getEndBorder();
        String search = request.getSearch();
        List<PriceByRegionDTO> data = priceDAO.getLimitedQuantityProductPricesInRegions(id, start, length, sort, search);
        int size = priceDAO.getCountPriceByPlace(search, id);
        return ListHolder.create(data, size);
    }

}

