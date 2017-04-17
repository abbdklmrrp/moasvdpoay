package nc.nut.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {
    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    
    //TODO add DataSource bean

//  @Bean(name = "dataSource")
//  public DataSource objectMapper() {
//    return new ObjectMapper();
//  }
}
