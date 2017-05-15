package jtelecom.dao.place;

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

    List<Place> getPlacesForFillInTariff();

    Integer getPlaceIdByName(String placeName);

}
