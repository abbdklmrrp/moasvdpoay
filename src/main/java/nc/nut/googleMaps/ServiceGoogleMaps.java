package nc.nut.googleMaps;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Anton Bulgakov
 * @since 21.04.2017
 */
public class ServiceGoogleMaps {
    private static String apiKey = "AIzaSyBF2CMlYRcV-9zTHFND2m2InqbYdeyJz30";
    private static Logger logger = LoggerFactory.getLogger(nc.nut.googleMaps.ServiceGoogleMaps.class);

    /**
     * Method gets region according to address from params and returns it.
     * If no region according to address from params, it will return NULL.
     *
     * @param address input address.
     * @return region of address from param.
     */
    public String getRegion(String address) {
        String region = null;
        GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context, address).await();
            if (results != null && results.length != 0) {
                // if it`s region
                region = getAddressComponent(results, "administrative_area_level_1");
                // if it`s city-region
                if (region == null) {
                    region = getAddressComponent(results, "administrative_area_level_2");
                }
            }
        } catch (ApiException | InterruptedException | IOException e) {
            logger.error("Can`t find this address.", e);
        }
        return region;
    }

    /**
     * Method returns part of address from GoogleMaps according to address component name from params.
     * If addressComponentName is null, method returns null.
     *
     * @param geoResults           array of data about address from GoogleMaps
     * @param addressComponentName component name of data from GoogleMaps
     * @return part of address according to address component from params.
     */
    private String getAddressComponent(GeocodingResult[] geoResults, String addressComponentName) {
        String region = null;
        if (addressComponentName != null) {
            AddressComponent[] components = geoResults[0].addressComponents;
            for (int i = 0; i < components.length; i++) {
                AddressComponentType[] type = components[i].types;
                if (type[0].toString().equals(addressComponentName)) {
                    region = components[i].longName;
                    break;
                }
            }
        }
        return region;
    }

    /**
     * Method gets formatted address according to address from params and returns it.
     * If no address according to address from params, it will return NULL.
     *
     * @param address input address.
     * @return formatted address.
     */
    public String getFormattedAddress(String address) {
        String formattedAddress = null;
        GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context, address).await();
            if (results != null && results.length != 0) {
                formattedAddress = results[0].formattedAddress;
            }
        } catch (ApiException | InterruptedException | IOException e) {
            logger.error("Can`t find this address.", e);
        }
        return formattedAddress;
    }
}