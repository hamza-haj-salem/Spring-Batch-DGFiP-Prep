package springBatch.batch.step;

import springBatch.batch.processor.GirItemProcessor;
import springBatch.batch.reader.GirItemReaderConfig;
import springBatch.dto.GirXmlDto;
import springBatch.entity.Declaration;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class GirStepConfig {
//Voilà le traitement batch que je veux exécuter : STEPS 
// on peut met ca avec GirJobConfig
    @Bean
    public Step girStep(JobRepository jobRepository,
                        PlatformTransactionManager transactionManager,
                        ItemReader<GirXmlDto> girReader,
                        GirItemProcessor girProcessor,
                        ItemWriter<Declaration> girWriter) {

        return new StepBuilder("girStep", jobRepository)
                .<GirXmlDto, Declaration>chunk(2, transactionManager)
                .reader(girReader)
                .processor(girProcessor)
                .writer(girWriter)
                .build();
    }
}