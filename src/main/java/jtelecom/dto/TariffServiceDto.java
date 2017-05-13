package jtelecom.dto;

/**
 * Created by Anna Rysakova on 08.05.2017.
 */
public class TariffServiceDto {
    private Integer idTariff;
    private Integer idService;

    public TariffServiceDto() {
    }

    public Integer getIdTariff() {
        return idTariff;
    }

    public void setIdTariff(Integer idTariff) {
        this.idTariff = idTariff;
    }

    public Integer getIdService() {
        return idService;
    }

    public void setIdService(Integer idService) {
        this.idService = idService;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("TariffServiceDto{")
                .append("idTariff=").append(idTariff)
                .append(", idService=").append(idService)
                .append('}').toString();
    }
}
