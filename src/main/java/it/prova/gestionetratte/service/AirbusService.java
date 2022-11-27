package it.prova.gestionetratte.service;

import java.util.List;

import it.prova.gestionetratte.dto.AirbusConTratteDTO;
import it.prova.gestionetratte.model.Airbus;


public interface AirbusService {
	
	List<Airbus> listAllElements(boolean eager);
	
	List<Airbus> listAllEager();

	Airbus caricaSingoloElemento(Long id);

	Airbus caricaSingoloElementoEager(Long id);

	Airbus aggiorna(Airbus airbusInstance);

	Airbus inserisciNuovo(Airbus airbusInstance);

	void rimuovi(Long idToRemove);
	
	Airbus findByCodiceAndDescrizione(String codice, String descrizione);

	List<Airbus> findByExample(Airbus example);
	
	List<AirbusConTratteDTO> findListaAirbusEvidenziandoSovrapposizioni();

}
