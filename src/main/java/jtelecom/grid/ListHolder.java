package jtelecom.grid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.NONE)
@JsonTypeName("listData")
public class ListHolder<T> {
    @JsonProperty("data")
    List<T> data = new ArrayList<T>();

    @JsonProperty("total")
    int totalCount;

    protected ListHolder() {

    }

    private ListHolder(List<T> data, long totalCount) {
        this.data.addAll(data);
        this.totalCount = (int) totalCount;
    }

    public static <T> ListHolder<T> create(List<T> data, long totalCount) {
        return new ListHolder<>(data, totalCount);
    }

    public List<T> getData() {
        return new ArrayList<>(data);
    }

    public int getTotalCount() {
        return totalCount;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("ListHolder{")
                .append("data size=").append(data.size())
                .append(", totalCount=").append(totalCount)
                .append('}').toString();
    }
}