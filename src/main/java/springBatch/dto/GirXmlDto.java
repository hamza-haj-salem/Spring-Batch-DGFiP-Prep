package springBatch.dto;

import jakarta.xml.bind.annotation.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "GIR")
@XmlAccessorType(XmlAccessType.FIELD)
public class GirXmlDto {

    @XmlElement(name = "MessageSpec")
    private MessageSpecDto messageSpec;

    @XmlElement(name = "ReportingEntity")
    private ReportingEntityDto reportingEntity;
}