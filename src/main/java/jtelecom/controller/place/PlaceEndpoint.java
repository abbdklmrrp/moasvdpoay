package jtelecom.controller.place;

import jtelecom.dao.place.Place;
import jtelecom.dao.place.PlaceDAO;
import jtelecom.dto.PriceByRegionDto;
import jtelecom.grid.GridRequestDto;
import jtelecom.grid.ListHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Rysakova Anna on 26.04.2017.
 */
@RestController
@RequestMapping({"admin"})
public class PlaceEndpoint {
    private static Logger logger = LoggerFactory.getLogger(PlaceEndpoint.class);
    @Resource
    private PlaceDAO placeDAO;

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
    public ListHolder allPricesByPlace(@ModelAttribute GridRequestDto request,
                                       @PathVariable(value = "id") Integer placeId) {
        String sort = request.getSort();
        int start = request.getStartBorder();
        int length = request.getLength();
        String search = request.getSearch();
        List<PriceByRegionDto> data = placeDAO.getLimitedQuantityPriceByPlace(placeId, start, length, sort, search);
        int size = placeDAO.getCountPlacesWithSearch(search);
        return ListHolder.create(data, size);
    }

}

