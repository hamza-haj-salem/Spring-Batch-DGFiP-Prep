package springBatch.batch.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import springBatch.dto.GirXmlDto;
import springBatch.dto.MessageSpecDto;
import springBatch.dto.ReportingEntityDto;
import springBatch.entity.Declaration;

public class GirItemProcessorTest {


    @Test
    void shouldProcessValidGirXml() throws Exception {

        //test cheks jenkins -> Github depuis master 5
        // GIVEN : création du MessageSpec
        MessageSpecDto messageSpec = new MessageSpecDto();
        messageSpec.setMessageRefId("MSG001");
        messageSpec.setMessageTypeIndic("GIR101");


        // GIVEN : création du ReportingEntity
        ReportingEntityDto reportingEntity = new ReportingEntityDto();
        reportingEntity.setCompanyName("ABC GROUP");
        reportingEntity.setCountry("FR");
        reportingEntity.setFiscalYear(2025);


        // GIVEN : création du XML complet
        GirXmlDto girXmlDto = new GirXmlDto();
        girXmlDto.setMessageSpec(messageSpec);
        girXmlDto.setReportingEntity(reportingEntity);



        // WHEN : appel du processor
        GirItemProcessor processor = new GirItemProcessor();

        Declaration declaration = processor.process(girXmlDto);
        
        System.out.println("DECLARATION CREE SUIT A PROCESS "+declaration);


        // THEN : vérification
        assertEquals("OK", declaration.getStatus());
        assertEquals("ABC GROUP", declaration.getCompanyName(),"COMPANY NAME NOT OK");
        assertEquals("FR", declaration.getCountry(), "COUNTRY NOT OK, SHOULD BE FRR but was "+declaration.getCountry());
        assertEquals(2025, declaration.getFiscalYear());
        assertEquals("MSG001", declaration.getMessageRefId());
        assertEquals("GIR101", declaration.getMessageTypeIndic());

    }
}