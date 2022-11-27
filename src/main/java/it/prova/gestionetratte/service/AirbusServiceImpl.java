package it.prova.gestionetratte.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import it.prova.gestionetratte.dto.AirbusConTratteDTO;
import it.prova.gestionetratte.dto.AirbusDTO;
import it.prova.gestionetratte.dto.TrattaDTO;
import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.repository.airbus.AirbusRepository;
import it.prova.gestionetratte.web.api.exceprion.AirbusNotFoundException;
import it.prova.gestionetratte.web.api.exceprion.TrattaNotFoundException;

@Service
@Transactional(readOnly = true)
public class AirbusServiceImpl implements AirbusService {

	@Autowired
	private AirbusRepository repository;

	@Override
	public List<Airbus> listAllElements(boolean eager) {
		if (eager)
			return (List<Airbus>) repository.findAllAirbusEager();

		return (List<Airbus>) repository.findAll();
	}

	@Override
	public List<Airbus> listAllEager() {
		return (List<Airbus>) repository.findAllEager();
	}

	@Override
	public Airbus caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Airbus caricaSingoloElementoEager(Long id) {
		return repository.findByIdEager(id);
	}

	@Transactional
	public Airbus aggiorna(Airbus airbusInstance) {
		return repository.save(airbusInstance);
	}

	@Transactional
	public Airbus inserisciNuovo(Airbus airbusInstance) {
		return repository.save(airbusInstance);
	}

	@Transactional
	public void rimuovi(Long idToRemove) {
		Airbus airbusToBeRemoved = repository.findByIdEager(idToRemove);

		if (airbusToBeRemoved == null)
			throw new AirbusNotFoundException("Airbus not found con id: " + idToRemove);

		if (airbusToBeRemoved.getTratte().size() > 0)
			throw new TrattaNotFoundException("Impossibile eliminare airbus: sono presenti tratte ad esso associate.");

		repository.deleteById(idToRemove);

	}

	public Airbus findByCodiceAndDescrizione(String codice, String descrizione) {
		return repository.findByCodiceAndDescrizione(codice, descrizione);
	}

	@Override
	public List<Airbus> findByExample(Airbus example) {
		return repository.FindByExample(example);
	}

	@Override
	public List<AirbusConTratteDTO> findListaAirbusEvidenziandoSovrapposizioni() {
		List<AirbusConTratteDTO> listAirbusEager = AirbusConTratteDTO
				.createAirbusDTOListFromModelList(repository.findAllEager(), true);
		for (AirbusConTratteDTO airbusItem : listAirbusEager) {
			for (TrattaDTO trattaItem : airbusItem.getTratte()) {
				for (TrattaDTO item : airbusItem.getTratte()) {
					if ((item.getData().isEqual(trattaItem.getData())
							&& item.getOraDecollo().isAfter(trattaItem.getOraDecollo())
							&& item.getOraDecollo().isBefore(trattaItem.getOraAtterraggio()))
							|| (item.getData().isEqual(trattaItem.getData())
									&& item.getOraAtterraggio().isAfter(trattaItem.getOraDecollo())
									&& item.getOraAtterraggio().isBefore(trattaItem.getOraAtterraggio()))) {
						airbusItem.setSovrapposizioni(null);

					}
				}
			}
		}

		return listAirbusEager;
	}

}
