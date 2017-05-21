package jtelecom.dao.place;

import jtelecom.dto.PriceByRegionDto;

import java.util.List;

/**
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

    Integer getPlaceIdByName(String placeName);

    List<Place> getLimitedQuantityPlace(int start, int length, String sort, String search);

    List<PriceByRegionDto> getLimitedQuantityPriceByPlace(int placeId, int start, int length, String sort, String search);

    Integer getCountPlacesWithSearch(String search);

    String getPlaceNameById(int id);

    Integer getCountPriceByPlace(String search, Integer placeId);

}
