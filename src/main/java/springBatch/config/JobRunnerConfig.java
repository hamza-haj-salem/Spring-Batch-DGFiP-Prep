package springBatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobRunnerConfig {

//    @Bean
//    public ApplicationRunner runJob(JobLauncher jobLauncher, Job girJob) {
//
//        return args -> {
//
//            JobParameters params = new JobParametersBuilder()
//                    .addLong("time", System.currentTimeMillis())
//                    .toJobParameters();
//
//            jobLauncher.run(girJob, params);
//
//            System.out.println("👉 GIR Job lancé automatiquement");
//        };
//    }
}