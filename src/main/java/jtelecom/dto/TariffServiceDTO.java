package jtelecom.dto;

/**
 * @author Anna Rysakova
 */
public class TariffServiceDTO {
    private Integer tariffId;
    private Integer serviceId;
    private Integer categoryId;
    private String serviceName;
    private String categoryName;

    public TariffServiceDTO() {
    }

    public Integer getTariffId() {
        return tariffId;
    }

    public void setTariffId(Integer tariffId) {
        this.tariffId = tariffId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("TariffServiceDTO{")
                .append("tariffId=").append(tariffId)
                .append(", serviceId=").append(serviceId)
                .append(", categoryId=").append(categoryId)
                .append(", serviceName='").append(serviceName).append('\'')
                .append(", categoryName='").append(categoryName).append('\'')
                .append('}').toString();
    }
}
