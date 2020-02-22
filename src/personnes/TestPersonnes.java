package personnes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import personnes.Joueur.NameTooLongException;
import personnes.Joueurs.PlayerAlreadyExistsException;

class TestPersonnes {

	@Test
	void testJoueur() {
		// Test constructeur et getters
		Joueur j = null;
		try {
			j = new Joueur(" AZe Rty ");
		} catch (NameTooLongException e) {
			fail("Erreur d'exception : NameTooLongException");
			return;
		}
		assertEquals(j.getNom(), "azerty");
		assertEquals(j.getScore(), 0);
		
		// Test incrémente score
		j.incrémenteScore();
		assertEquals(j.getScore(), 1);
		
		// Test toString
		assertEquals(j.toString(), "1                  azerty");
		
		// Test exception NameTooLongException
		assertThrows(Joueur.NameTooLongException.class, () -> {
			new Joueur("aaaaaaaaaaaaaaaaaaaaa");
		});
	}

	
	@Test
	void testJoueurs() {
		// Test constructeur, addJoueur et getJoueur
		Joueurs js = new Joueurs();
		try {
			js.addJoueur("azerty");
		} catch (Exception e) {
			fail("Erreur d'exception");
		}
		assertEquals(js.getJoueur("azerty").getNom(), "azerty");
		assertNull(js.getJoueur("azerty "));
		
		// Test Exception PlayerAlreadyExistsException
		assertThrows(PlayerAlreadyExistsException.class, () -> {
			js.addJoueur("azerty");
		});
		
		// Test exists
		assertTrue(js.exists("azerty"));
		assertFalse(js.exists("azerty "));
		
		// Test removeJoueur
		js.removeJoueur("azerty");
		assertNull(js.getJoueur("azerty"));
		
		// Test toString
		try {
			js.addJoueur("aqw");
			js.addJoueur("zsx");
			js.addJoueur("edc");
			js.addJoueur("rfv");
		} catch (Exception e2) {
			fail("Erreur d'exception");
		}
		assertEquals(js.toString(),
				"aqw\n"
				+ "zsx\n"
				+ "edc\n"
				+ "rfv\n");
		
		// Test afficherScores
		js.getJoueur("zsx").incrémenteScore();
		js.getJoueur("zsx").incrémenteScore();
		js.getJoueur("zsx").incrémenteScore();
		js.getJoueur("zsx").incrémenteScore();
		js.getJoueur("rfv").incrémenteScore();
		js.getJoueur("rfv").incrémenteScore();
		js.getJoueur("edc").incrémenteScore();
		assertEquals(js.afficherScores(),
				"SCORE              JOUEUR\n"
				+ "=========================\n"
				+ "4                     zsx\n"
				+ "2                     rfv\n"
				+ "1                     edc\n"
				+ "0                     aqw\n");
	}
}
