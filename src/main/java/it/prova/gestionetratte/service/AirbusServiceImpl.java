package it.prova.gestionetratte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

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

}
