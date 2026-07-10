package springBatch.batch.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import springBatch.dto.GirXmlDto;
import springBatch.dto.MessageSpecDto;
import springBatch.dto.ReportingEntityDto;
import springBatch.entity.Declaration;
import springBatch.repository.DeclarationRepository;

@ExtendWith(MockitoExtension.class) //Utilise Mockito pendant l'exécution de
									//ce class de test
public class GirItemProcessorTest {
	
	 @Mock
    private DeclarationRepository declarationRepository ;
	private GirItemProcessor processor;
	    @BeforeEach
	    void setup(){
	        processor = new GirItemProcessor(declarationRepository);
	    }
	
    @Test
    void shouldProcessValidGirXml() throws Exception {
        //test lancement PR dans jenkins depuis branche 3
        //test cheks jenkins 2 -> Github depuis branche 2
        //test cheks jenkins -> Github depuis branche 1
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
        
       // GirItemProcessor processor = new GirItemProcessor(declarationRepository);
      

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

    @Test
    void shouldRejectDeclarationWhenCompanyNameIsMissing() throws Exception {

        // GIVEN
        MessageSpecDto messageSpec = new MessageSpecDto();
        messageSpec.setMessageRefId("MSG002");
        messageSpec.setMessageTypeIndic("GIR101");

        ReportingEntityDto reportingEntity = new ReportingEntityDto();
        reportingEntity.setCompanyName(null);
        reportingEntity.setCountry("FR");
        reportingEntity.setFiscalYear(2025);

        GirXmlDto dto = new GirXmlDto();
        dto.setMessageSpec(messageSpec);
        dto.setReportingEntity(reportingEntity);

        // WHEN
        GirItemProcessor processor = new GirItemProcessor(declarationRepository);

        Declaration declaration = processor.process(dto);

        // THEN
        assertEquals("REJECTED", declaration.getStatus());
    }

    @Test
    void shouldReturnErrorWhenMessageTypeIndicIsInvalid() throws Exception {

        // GIVEN
        MessageSpecDto messageSpec = new MessageSpecDto();
        messageSpec.setMessageRefId("MSG003");
        messageSpec.setMessageTypeIndic("GIR999");

        ReportingEntityDto reportingEntity = new ReportingEntityDto();
        reportingEntity.setCompanyName("ABC GROUP");
        reportingEntity.setCountry("FR");
        reportingEntity.setFiscalYear(2025);

        GirXmlDto dto = new GirXmlDto();
        dto.setMessageSpec(messageSpec);
        dto.setReportingEntity(reportingEntity);

        // WHEN
        GirItemProcessor processor = new GirItemProcessor(declarationRepository);

        Declaration declaration = processor.process(dto);

        // THEN
        assertEquals("ERROR", declaration.getStatus());
    }
}