package nc.nut.util;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Sergiy Dyrda
 */
public class PropertiesUtil {
    public static Properties loadProperties(String resourceName) {
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties(resourceName);
            if (properties.isEmpty()) {
                throw new IllegalStateException("File " + resourceName + " is not exist or not valid");
            }
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + resourceName, e);
        }
    }
}
