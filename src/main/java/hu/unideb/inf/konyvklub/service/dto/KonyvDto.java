package hu.unideb.inf.konyvklub.service.dto;

import lombok.Data;

@Data
public class KonyvDto {
    private String id;
    private String tulajdonosUid;
    private String tulajdonosNev;
    private String cim;
    private String iro;
    private String konyvInfo;     // Mit kell tudni a könyvről
    private String tartalomInfo;  // Mit kell tudni a tartalomról
}
