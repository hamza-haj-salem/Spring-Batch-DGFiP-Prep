package springBatch.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    @GetMapping("/gir/executions")
    public ResponseEntity<?> getExecutions() {

        var instances = jobExplorer.getJobInstances("girJob", 0, 100);

        List<Map<String, Object>> result = new ArrayList<>();
        
        DateTimeFormatter formatter = 
    	        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        for (var instance : instances) {

            var executions = jobExplorer.getJobExecutions(instance);

            for (var execution : executions) {

                Map<String, Object> item = new HashMap<>();

                item.put("id", execution.getId());
                item.put("status", execution.getStatus());
                item.put("startTime", execution.getStartTime()!= null ?
                		execution.getStartTime().format(formatter)
                	    : null);
                item.put("endTime", execution.getEndTime()!= null ?
                		execution.getStartTime().format(formatter)
                	    : null);
                item.put("exitCode",
                        execution.getExitStatus().getExitCode());

                result.add(item);
            }
        }

        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/gir/lastJobInstance")
    public ResponseEntity<?> getLastJobStatus() {

        var instances = jobExplorer.getJobInstances("girJob", 0, 1);

        if (instances.isEmpty()) {
            return ResponseEntity.ok(Map.of("status", "Aucun job"));
        }

        var executions = jobExplorer.getJobExecutions(instances.get(0));
        var lastExecution = executions.iterator().next();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        Map<String, Object> result = new HashMap<>();

        result.put("jobName", lastExecution.getJobInstance().getJobName());
        result.put("status", lastExecution.getStatus().toString());

        result.put("startTime",
                lastExecution.getStartTime() != null
                        ? lastExecution.getStartTime().format(formatter)
                        : null);

        result.put("endTime",
                lastExecution.getEndTime() != null
                        ? lastExecution.getEndTime().format(formatter)
                        : null);

        result.put("exitCode",
                lastExecution.getExitStatus().getExitCode());

        String reason = "";
        String firstLineException = lastExecution.getExitStatus().getExitDescription().split("\n")[0];
        reason = firstLineException.substring(firstLineException.indexOf(":") + 1).trim();

        result.put("reason", reason);

        return ResponseEntity.ok(result);
    }
  /*  
    @GetMapping("/gir/lastJob") public JobInstance getLastJob() {
    	var instances = jobExplorer.getJobInstances("girJobHamza", 0, 1);
    	return jobExplorer.getLastJobInstance("girJob");
    	}
    	*/
    
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
    
    @GetMapping("/gir/executions2")
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