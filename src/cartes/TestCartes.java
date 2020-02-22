package cartes;

import org.junit.jupiter.api.Test;

import cartes.CarteObjectif.ImpossibleMoveException;
import cartes.CarteObjectif.PodiumTooLongException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

class TestCartes {
	
	@Test
	void testCarteObjectif() {
		
		// Test constructeur et getters
		LinkedList<Animal> s1 = new LinkedList<Animal>();
		LinkedList<Animal> s2 = new LinkedList<Animal>();
		s1.add(Animal.LION);
		s1.add(Animal.OURS);
		s2.add(Animal.ELEPHANT);
		CarteObjectif c = null;
		try {
			c = new CarteObjectif(s1, s2);
		} catch (PodiumTooLongException e) {
			fail("Erreur d'exception");
			return;
		}
		
		assertTrue(c.getPodiumBleu().equals(s1));
		assertTrue(c.getPodiumRouge().equals(s2));
		
		// Test clone et estEgale
		CarteObjectif cc = c.clone();
		assertTrue(c.estEgale(cc));
		
		// Test effectuerMouvements
		LinkedList<Mouvement> m1 = new LinkedList<Mouvement>();
		m1.add(Mouvement.KI);
		m1.add(Mouvement.MA);
		m1.add(Mouvement.LO);
		m1.add(Mouvement.NI);

		LinkedList<Animal> s21 = new LinkedList<Animal>();
		LinkedList<Animal> s22 = new LinkedList<Animal>();
		s21.add(Animal.ELEPHANT);
		s21.add(Animal.LION);
		s22.add(Animal.OURS);
		CarteObjectif c2 = null;
		try {
			c2 = new CarteObjectif(s21, s22);
		} catch (PodiumTooLongException e1) {
			fail("Erreur d'exception");
		}
		
		try {
			assertTrue(c.effectuerMouvements(m1, c2));
		} catch (ImpossibleMoveException e) {
			fail("Erreur d'exception");
		}
		
		// Test estPossible
		assertTrue(c.estPossible(Mouvement.KI));
		assertTrue(c.estPossible(Mouvement.LO));
		assertTrue(c.estPossible(Mouvement.SO));
		assertTrue(c.estPossible(Mouvement.NI));
		assertFalse(c.estPossible(Mouvement.MA));
		
		CarteObjectif n = new CarteObjectif();
		assertFalse(n.estPossible(Mouvement.KI));
		assertFalse(n.estPossible(Mouvement.LO));
		assertFalse(n.estPossible(Mouvement.SO));
		assertFalse(n.estPossible(Mouvement.NI));
		assertFalse(n.estPossible(Mouvement.MA));
	}
}
