package it.prova.gestionetratte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.repository.airbus.AirbusRepository;

@Service
@Transactional(readOnly = true)
public class AirbusServiceImpl implements AirbusService{
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Airbus caricaSingoloElementoEager(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Airbus aggiorna(Airbus airbusInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public Airbus inserisciNuovo(Airbus airbusInstance) {
		return repository.save(airbusInstance);
	}

	@Override
	public void rimuovi(Long idToRemove) {
		// TODO Auto-generated method stub
		
	}

	public Airbus findByCodiceAndDescrizione(String codice, String descrizione) {
		return repository.findByCodiceAndDescrizione(codice, descrizione);
	}

}
