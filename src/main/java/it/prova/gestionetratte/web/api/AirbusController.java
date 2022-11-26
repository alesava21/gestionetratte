package it.prova.gestionetratte.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestionetratte.dto.AirbusDTO;
import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.service.AirbusService;
import it.prova.gestionetratte.web.api.exceprion.AirbusNotFoundException;
import it.prova.gestionetratte.web.api.exceprion.IdNotNullForInsertException;

@RestController
@RequestMapping("api/airbus")
public class AirbusController {

	@Autowired
	private AirbusService airbusService;

	@GetMapping
	public List<AirbusDTO> getAll() {
		// senza DTO qui hibernate dava il problema del N + 1 SELECT
		// (probabilmente dovuto alle librerie che serializzano in JSON)
		return AirbusDTO.createAirbusDTOListFromModelList(airbusService.listAllElements(true), true);
	}

	@PostMapping("/search")
	public List<AirbusDTO> search(@RequestBody AirbusDTO example) {
		return AirbusDTO.createAirbusDTOListFromModelList(airbusService.findByExample(example.buildAirbusModel()),
				false);
	}

	@GetMapping("/{id}")
	public AirbusDTO findById(@PathVariable(value = "id", required = true) long id) {
		Airbus airbus = airbusService.caricaSingoloElementoEager(id);

		if (airbus == null)
			throw new AirbusNotFoundException("Airbus not found con id: " + id);

		return AirbusDTO.buildAirbusDTOFromModel(airbus, true);
	}
	

}
