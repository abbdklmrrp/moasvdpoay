package jtelecom.dao.place;

import jtelecom.dto.PriceByRegionDTO;

import java.util.List;

/**
 * This interface has methods to work with places.
 *
 * @author Anna Rysakova
 * @author Revniuk Aleksandr
 */
public interface PlaceDAO {
    /**
     * This method returns all places.
     *
     * @return list of places
     */
    List<Place> getAll();

    List<Place> getAllPlaces();

    List<Place> getLimitedQuantityPlace(int start, int length, String sort, String search);

    List<PriceByRegionDTO> getLimitedQuantityPriceByPlace(int placeId, int start, int length, String sort, String search);

    Integer getCountPlacesWithSearch(String search);

    String getPlaceNameById(int id);

    Integer getCountPriceByPlace(String search, Integer placeId);

}
