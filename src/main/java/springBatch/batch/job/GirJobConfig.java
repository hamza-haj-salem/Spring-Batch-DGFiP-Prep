package springBatch.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GirJobConfig {
	//Voilà le traitement batch que je veux exécuter : JOB 
	// on peut met ici avec GirStepConfig
    @Bean
    public Job girJob(JobRepository jobRepository, Step girStep) {
        return new JobBuilder("girJob", jobRepository)
                .start(girStep)
                .build();
    }

}