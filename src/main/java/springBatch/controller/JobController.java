package springBatch.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import springBatch.entity.Declaration;
import springBatch.repository.DeclarationRepository;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobLauncher jobLauncher;
    private final Job girJob;
    private final JobExplorer jobExplorer;
    private final DeclarationRepository declarationRepository;

    public JobController(JobLauncher jobLauncher,
    		Job girJob,
    		JobExplorer jobExplorer,
    		DeclarationRepository declarationRepository) {
    	
        this.jobLauncher = jobLauncher;
        this.girJob = girJob;
        this.jobExplorer = jobExplorer;
        this.declarationRepository = declarationRepository;
    }

    @PostMapping("/gir/run")
    public ResponseEntity<String> runGirJob() {

        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // important pour relancer le job
                    .toJobParameters();

            jobLauncher.run(girJob, params);

            return ResponseEntity.ok("Gir Job lancé avec succès");

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur lancement job : " + e.getMessage());
        }
    }
    
    @GetMapping("/gir/status") public ResponseEntity<String> getLastJobStatus() {
    	var instances = jobExplorer.getJobInstances("girJob", 0, 1);
    	if (instances.isEmpty()) {
    		return ResponseEntity.ok("Aucun job trouvé");
    	}
    	
    	var executions = jobExplorer.getJobExecutions(instances.get(0));
    	var lastExecution = executions.iterator().next();
    	return ResponseEntity.ok( "Job status = " + lastExecution.getStatus() ); 
    	}
    
    @GetMapping("/gir/lastJob") public JobInstance getLastJob() {
    	var instances = jobExplorer.getJobInstances("girJobHamza", 0, 1);
    	return jobExplorer.getLastJobInstance("girJobHamza");
    	}
    
    @GetMapping("/gir/{idJob}") public JobInstance getJobById(@PathVariable Long idJob) {
    	var instances = jobExplorer.getJobInstances("girJob", 0, 1);
    	return jobExplorer.getJobInstance(idJob);
    	}
    
    @GetMapping("/gir/failed") public ResponseEntity<String> getFailedJobs() {
    	var instances = jobExplorer.getJobInstances("girJob", 0, 100);
    	List<JobExecution> jobExecutions = instances.stream()
                .flatMap(instance -> jobExplorer.getJobExecutions(instance).stream())
                .filter(inst -> BatchStatus.FAILED.equals(inst.getStatus()))
                .collect(Collectors.toList());
    	
    	 return ResponseEntity.ok(jobExecutions.toString());
    	}
    
    @GetMapping("/gir/executions")
    public ResponseEntity<List<String>> getAllExecutions() {

        List<JobInstance> instances = jobExplorer.getJobInstances("girJob", 0, 10);

        if (instances.isEmpty()) {
            return ResponseEntity.ok(List.of("Aucune exécution trouvée"));
        }

        List<String> result = instances.stream()
                .flatMap(instance -> jobExplorer.getJobExecutions(instance).stream())
                .map(exec -> "ExecutionId=" + exec.getId()
                        + " | Status=" + exec.getStatus()
                        + " | Start=" + exec.getStartTime()
                        + " | End=" + exec.getEndTime())
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
    
  
    
    @GetMapping("/{id}")
    public ResponseEntity<Declaration> getById(@PathVariable Long id) {

        return declarationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}