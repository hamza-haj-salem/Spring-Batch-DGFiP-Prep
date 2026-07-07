package springBatch.batch.writer;

import springBatch.entity.Declaration;
import springBatch.repository.DeclarationRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GirItemWriterConfig {
// writer reçoit les objets Declaration validés et les enregistres dans PostgreSQL.
    @Bean
    public ItemWriter<Declaration> girWriter(DeclarationRepository repository) {

        return new ItemWriter<Declaration>() {

            @Override
            public void write(Chunk<? extends Declaration> chunk) {

                repository.saveAll(chunk.getItems());

                System.out.println("👉 " + chunk.getItems().size() + " déclarations enregistrées");
            }
        };
    }
}