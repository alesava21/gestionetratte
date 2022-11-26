package it.prova.gestionetratte.repository.airbus;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionetratte.model.Airbus;

public interface AirbusRepository extends CrudRepository<Airbus, Long>{
	Airbus findByCodiceAndDescrizione(String codice, String descrizione);
	
	@Query("select a from Airbus a join fetch a.tratte")
	List<Airbus> findAllAirbusEager();
	
	@Query("from Airbus a left join fetch a.tratte where a.id=?1")
	Airbus findByIdEager(Long id);

}
