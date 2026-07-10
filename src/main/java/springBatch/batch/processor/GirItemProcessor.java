package springBatch.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import springBatch.dto.GirXmlDto;
import springBatch.entity.Declaration;
import springBatch.exceptions.refIdExistException;
import springBatch.repository.DeclarationRepository;


@Component
public class GirItemProcessor implements ItemProcessor<GirXmlDto, Declaration> {
//le process ici : transforme le DTO XML en entit Declaration selon des regles
	
	 public DeclarationRepository declarationRepository;

	public GirItemProcessor(DeclarationRepository declarationRepository) {
	    this.declarationRepository = declarationRepository;
	 }

	@Override
	public Declaration process(GirXmlDto item) {

	    String type = item.getMessageSpec().getMessageTypeIndic();

	    Declaration d = new Declaration();

		d.setMessageRefId(item.getMessageSpec().getMessageRefId());
	    d.setMessageTypeIndic(type);

	    d.setCompanyName(item.getReportingEntity().getCompanyName());
	    d.setCountry(item.getReportingEntity().getCountry());
	    d.setFiscalYear(item.getReportingEntity().getFiscalYear());

		if(item.getReportingEntity().getCompanyName() == null){
			d.setStatus("REJECTED");
		}else{
			d.setStatus("OK");
		}

		if (!"GIR101".equals(type)
				&& !"GIR102".equals(type)
				&& !"GIR103".equals(type)) {

			d.setStatus("ERROR");
		}
		if(declarationRepository.existsByMessageRefId(
		        item.getMessageSpec().getMessageRefId())){

		    throw new refIdExistException(
		        "Le MessageRefId existe déjà."
		    );

		}

	    return d;
	}
}