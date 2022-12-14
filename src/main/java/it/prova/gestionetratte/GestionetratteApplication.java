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
		String descrizione = "Roma - bangkok";
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

		Tratta trattaRomaBangkok = new Tratta("HSYYDHDI", "Roma - bangkok", localDate, localTimeObjDecollo,
				localTimeObjPartenza, Stato.ATTIVA, airbusNew);
		if (trattaService.findByCodiceAndDescrizione(trattaRomaBangkok.getCodice(), trattaRomaBangkok.getDescrizione())
				.isEmpty()) {
			trattaService.inserisciNuovo(trattaRomaBangkok);
		}

		String codiceLondraMilano = "GSTDJHSGDYDJ";
		String descrizioneLondraMilano = "Londra - Milano";
		Integer numeroPasseggeriAirbus = 250;

		DateTimeFormatter formatterAirbusLondraMilano = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String AirbusLondraMilano = "26/11/2022";
		LocalDate localDateAirbusLondraMilano = LocalDate.parse(AirbusLondraMilano, formatterAirbusLondraMilano);

		Airbus airbusLondraMilano = airbusService.findByCodiceAndDescrizione(codiceLondraMilano,
				descrizioneLondraMilano);

		if (airbusLondraMilano == null) {
			airbusLondraMilano = new Airbus(codiceLondraMilano, descrizioneLondraMilano, localDateAirbusLondraMilano,
					numeroPasseggeriAirbus);
			airbusService.inserisciNuovo(airbusLondraMilano);
		}

		DateTimeFormatter formatterDataTrattaLondraMilano = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String datePartenzaLondraMilano = "16/08/2022";
		LocalDate localDateLondraMilano = LocalDate.parse(datePartenzaLondraMilano, formatterDataTrattaLondraMilano);

		DateTimeFormatter formatterOraDecolloLondra = DateTimeFormatter.ofPattern("HH.mm");
		String timeLondra = "05.40";
		LocalTime localTimeObjDecolloLondra = LocalTime.parse(timeLondra, formatterOraDecolloLondra);

		DateTimeFormatter formatterOraAtterraggioMilano = DateTimeFormatter.ofPattern("HH.mm");
		String timeAtterraggioMilano = "10.20";
		LocalTime localTimeObjAtterraggioMilano = LocalTime.parse(timeAtterraggioMilano, formatterOraAtterraggioMilano);

		Tratta trattaLondraMilano = new Tratta("HSVSDHGCSA", "Londra - Milano", localDateLondraMilano,
				localTimeObjDecolloLondra, localTimeObjAtterraggioMilano, Stato.CONCLUSA, airbusLondraMilano);
		if (trattaService
				.findByCodiceAndDescrizione(trattaLondraMilano.getCodice(), trattaLondraMilano.getDescrizione())
				.isEmpty())
			trattaService.inserisciNuovo(trattaLondraMilano);
	}

}
