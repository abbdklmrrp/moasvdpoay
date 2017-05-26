package jtelecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Created by Anna Rysakova on 06.05.2017.
 */
// FIXME: 11.05.2017  services
@JsonTypeName("category")
public class ServicesByCategoryDTO {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;

    public ServicesByCategoryDTO() {
    }

    ServicesByCategoryDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("ServicesByCategoryDTO{")
                .append("id=").append(id)
                .append(", name='").append(name)
                .append('\'').append('}').toString();
    }
}
