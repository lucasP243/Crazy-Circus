/**
 * @file Deck.java
 * @brief classe Deck du projet Crazy Circus et exceptions relatives
 * @version 1.0 - 04.03.2019
 * @author PINARD Lucas & Amiot Tanguy
 */
package cartes;

import java.util.LinkedList;
import java.util.Random;

import cartes.CarteObjectif.PodiumTooLongException;

/** Classe Deck */
public class Deck {

	/** Le deck de CarteObjectif */
	private LinkedList<CarteObjectif> deck;

	/**
	 * Construit un deck en générant toute ses cartes.
	 *
	 * @throws AlgorithmFailedExeption si la génération des cartes échoue
	 */
	public Deck() throws AlgorithmFailedExeption {
		deck = new LinkedList<CarteObjectif>();
		try {
			this.générerCartes();
		} catch (PodiumTooLongException e) {
			throw new AlgorithmFailedExeption("La génération des cartes à échoué");
		}
	}

	/**
	 * Vérifie si le deck est vide.
	 *
	 * @return true si le deck est vide, false sinon
	 */
	public boolean estVide() {
		return deck.isEmpty();
	}

	/**
	 * Tirer au hasard une carte dans le deck.
	 *
	 * @return la carte tirée
	 */
	public CarteObjectif tirerCarte() {
		assert !deck.isEmpty();
		Random rd = new Random();
		return deck.remove(rd.nextInt(deck.size()));
	}

	/**
	 * Génère toutes les cartes possibles avec 3 animaux existants.
	 *
	 * @throws AlgorithmFailedExeption the algorithm failed exeption
	 * @throws PodiumTooLongException si une erreur survient au cours de l'algorithme
	 */
	private void générerCartes() throws PodiumTooLongException {
		LinkedList<Animal> bleu = new LinkedList<Animal>();
		LinkedList<Animal> rouge = new LinkedList<Animal>();
		for (Animal i : Animal.values())
			bleu.push(i);

		CarteObjectif référence = new CarteObjectif();
		référence.setPodiumBleu(bleu);
		référence.setPodiumRouge(rouge);
		
		CarteObjectif variant1 = référence.clone();
		
		int itération = 0;
		do {
			bleu = variant1.getPodiumBleu();
			rouge = variant1.getPodiumRouge();
			
			bleu.add(itération%2, bleu.remove(1 + itération%2));
			variant1.setPodiumBleu(bleu);
			deck.add(variant1.clone());
			
			deck.add(new CarteObjectif(rouge, bleu));
			
			rouge.add(bleu.pop());
			deck.add(new CarteObjectif(bleu, rouge));
			deck.add(new CarteObjectif(rouge, bleu));
			
			++itération;
		} while (!variant1.estEgale(référence));
	}
	
	/** Exception AlgorithmFailedExeption */
	public class AlgorithmFailedExeption extends Exception {
		
		/**
		 * Génère une exception AlgorithmFailedExeption
		 *
		 * @param s le message à associer à l'exception
		 */
		public AlgorithmFailedExeption(String s) {
			super(s);
		}
	}
}
