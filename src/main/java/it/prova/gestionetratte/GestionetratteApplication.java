package it.prova.gestionetratte;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Stato;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.service.AirbusService;
import it.prova.gestionetratte.service.TrattaService;

@SpringBootApplication
public class GestionetratteApplication implements CommandLineRunner {

	@Autowired
	private AirbusService airbusService;

	@Autowired
	private TrattaService trattaService;

	public static void main(String[] args) {
		SpringApplication.run(GestionetratteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String codice = "JSYSDHDU";
		String descrizione = "Roma - Bangock";
		Integer numeroPassegeri = 150;

		DateTimeFormatter formatterDataAirbus = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String dateAirbus = "16/08/2022";
		LocalDate localDateAirbus = LocalDate.parse(dateAirbus, formatterDataAirbus);

		Airbus airbusNew = airbusService.findByCodiceAndDescrizione(codice, descrizione);

		if (airbusNew == null) {
			airbusNew = new Airbus(codice, descrizione, localDateAirbus, numeroPassegeri);
			airbusService.inserisciNuovo(airbusNew);
		}

		DateTimeFormatter formatterDataTrattaRoma = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String datePartenzaRoma = "16/08/2022";
		LocalDate localDate = LocalDate.parse(datePartenzaRoma, formatterDataTrattaRoma);

		DateTimeFormatter formatterOraDecollo = DateTimeFormatter.ofPattern("HH.mm");
		String time = "08.20";
		LocalTime localTimeObjDecollo = LocalTime.parse(time, formatterOraDecollo);

		DateTimeFormatter formatterOraPartenza = DateTimeFormatter.ofPattern("HH.mm");
		String time1 = "10.20";
		LocalTime localTimeObjPartenza = LocalTime.parse(time1, formatterOraPartenza);

		Tratta tratta = new Tratta("HSYYDHDI", "Roma - Bangock", localDate, localTimeObjDecollo, localTimeObjPartenza,
				Stato.ATTIVA, airbusNew);
		if (trattaService.findByCodiceAndDescrizione(tratta.getCodice(), tratta.getDescrizione()).isEmpty())
			trattaService.inserisciNuovo(tratta);

	}

}
