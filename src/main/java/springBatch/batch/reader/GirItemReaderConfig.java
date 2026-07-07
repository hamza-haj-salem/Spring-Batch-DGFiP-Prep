package springBatch.batch.reader;

import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import springBatch.dto.GirXmlDto;

@Configuration
public class GirItemReaderConfig {
// Cette methode Lit le fichier gir.xml et crée des objets GirXmlDto.
    @Bean
    public StaxEventItemReader<GirXmlDto> girReader() {

        StaxEventItemReader<GirXmlDto> reader = new StaxEventItemReader<>();

        reader.setResource(new ClassPathResource("input/gir.xml"));

        reader.setFragmentRootElementName("GIR");

        // 🔥 IMPORTANT : MARSHALLER OBLIGATOIRE
        Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setClassesToBeBound(GirXmlDto.class);

        reader.setUnmarshaller(unmarshaller);

        return reader;
    }
}