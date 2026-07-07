package springBatch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	//cette class just Active Spring Batch
	//Grâce à @EnableBatchProcessing, Spring crée automatiquement
	//plusieurs beans techniques comme :
	//JobRepository
	//JobLauncher
	//JobExplorer
}