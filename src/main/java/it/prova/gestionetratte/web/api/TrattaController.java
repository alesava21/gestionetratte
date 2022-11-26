package it.prova.gestionetratte.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestionetratte.dto.AirbusDTO;
import it.prova.gestionetratte.dto.TrattaDTO;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.service.TrattaService;
import it.prova.gestionetratte.web.api.exceprion.IdNotNullForInsertException;

@RestController
@RequestMapping("api/tratta")
public class TrattaController {
	
	@Autowired
	private TrattaService trattaService;
	
	@GetMapping
	public List<TrattaDTO> getAll(){
		return TrattaDTO.createTrattaDTOListFromModelList(trattaService.listAllElements(true),true);
	}public TrattaController() {
		// TODO Auto-generated constructor stub
	}
	
	@PostMapping
	public TrattaDTO createNew (@Valid @RequestBody TrattaDTO trattaInput) { 
		if (trattaInput.getId() != null) 
			throw new IdNotNullForInsertException("Non e ammesso fornire un id per la creazione");
		
		Tratta trattaInserita = trattaService.inserisciNuovo(trattaInput.buildTrattaModel());
		return TrattaDTO.buildTrattaDTOFromModel(trattaInserita, true);  
	}
	
	@PostMapping("/search")
	public List<TrattaDTO> search(@RequestBody TrattaDTO example) {
		return TrattaDTO.createTrattaDTOListFromModelList(trattaService.findByExample(example.buildTrattaModel()),
				false);
	} 

}
