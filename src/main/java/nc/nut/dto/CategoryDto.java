package nc.nut.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Anna Rysakova on 06.05.2017.
 */
@XmlAccessorType(XmlAccessType.NONE)
@JsonTypeName("category")
public class CategoryDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;

    public CategoryDto() {
    }

    CategoryDto(int id, String name) {
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
}
