package springBatch.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "declaration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Declaration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identifiant du message GIR
    @Column(name = "message_ref_id", nullable = false, unique = true)
    private String messageRefId;

    // Type de message (GIR101, GIR102, GIR103)
    @Column(name = "message_type_indic", nullable = false)
    private String messageTypeIndic;

    // Nom de l'entreprise déclarée
    @Column(name = "company_name", nullable = false)
    private String companyName;

    // Pays
    @Column(name = "country")
    private String country;

    // Année fiscale
    @Column(name = "fiscal_year")
    private Integer fiscalYear;

    // Statut du traitement batch (OK / ERROR / REJECTED)
    @Column(name = "status")
    private String status;

    // Date d'import
    @Column(name = "import_date")
    private LocalDate importDate;
}