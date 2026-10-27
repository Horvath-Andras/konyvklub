package hu.unideb.inf.konyvklub.service.dto;

import lombok.Data;

@Data
public class BaratKeresDto {
    private String id;
    private String feladoUid;
    private String feladoNev;
    private String cimzettUid;
    private String statusz; // FUGGOBEN | ELFOGADVA
}
