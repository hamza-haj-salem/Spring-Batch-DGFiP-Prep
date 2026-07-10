package springBatch.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import springBatch.entity.Declaration;
import springBatch.repository.DeclarationRepository;


@RestController
@RequestMapping("/declarations")
public class DeclarationController {


    private final DeclarationRepository declarationRepository;


    public DeclarationController(DeclarationRepository declarationRepository) {
        this.declarationRepository = declarationRepository;
    }


    @GetMapping
    public ResponseEntity<List<Declaration>> getAllDeclarations() {

        return ResponseEntity.ok(
                declarationRepository.findAll()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getDeclarationById(
            @PathVariable Long id) {


        return declarationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(
                    ResponseEntity.notFound().build()
                );
    }

}