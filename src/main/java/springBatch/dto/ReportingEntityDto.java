package springBatch.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportingEntityDto {

    @XmlElement(name = "CompanyName")
    private String companyName;

    @XmlElement(name = "Country")
    private String country;

    @XmlElement(name = "FiscalYear")
    private Integer fiscalYear;
}
