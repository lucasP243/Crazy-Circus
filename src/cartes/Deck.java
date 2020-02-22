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
	 * Construit un deck en g�n�rant toute ses cartes.
	 *
	 * @throws AlgorithmFailedExeption si la g�n�ration des cartes �choue
	 */
	public Deck() throws AlgorithmFailedExeption {
		deck = new LinkedList<CarteObjectif>();
		try {
			this.g�n�rerCartes();
		} catch (PodiumTooLongException e) {
			throw new AlgorithmFailedExeption("La g�n�ration des cartes � �chou�");
		}
	}

	/**
	 * V�rifie si le deck est vide.
	 *
	 * @return true si le deck est vide, false sinon
	 */
	public boolean estVide() {
		return deck.isEmpty();
	}

	/**
	 * Tirer au hasard une carte dans le deck.
	 *
	 * @return la carte tir�e
	 */
	public CarteObjectif tirerCarte() {
		assert !deck.isEmpty();
		Random rd = new Random();
		return deck.remove(rd.nextInt(deck.size()));
	}

	/**
	 * G�n�re toutes les cartes possibles avec 3 animaux existants.
	 *
	 * @throws AlgorithmFailedExeption the algorithm failed exeption
	 * @throws PodiumTooLongException si une erreur survient au cours de l'algorithme
	 */
	private void g�n�rerCartes() throws PodiumTooLongException {
		LinkedList<Animal> bleu = new LinkedList<Animal>();
		LinkedList<Animal> rouge = new LinkedList<Animal>();
		for (Animal i : Animal.values())
			bleu.push(i);

		CarteObjectif r�f�rence = new CarteObjectif();
		r�f�rence.setPodiumBleu(bleu);
		r�f�rence.setPodiumRouge(rouge);
		
		CarteObjectif variant1 = r�f�rence.clone();
		
		int it�ration = 0;
		do {
			bleu = variant1.getPodiumBleu();
			rouge = variant1.getPodiumRouge();
			
			bleu.add(it�ration%2, bleu.remove(1 + it�ration%2));
			variant1.setPodiumBleu(bleu);
			deck.add(variant1.clone());
			
			deck.add(new CarteObjectif(rouge, bleu));
			
			rouge.add(bleu.pop());
			deck.add(new CarteObjectif(bleu, rouge));
			deck.add(new CarteObjectif(rouge, bleu));
			
			++it�ration;
		} while (!variant1.estEgale(r�f�rence));
	}
	
	/** Exception AlgorithmFailedExeption */
	public class AlgorithmFailedExeption extends Exception {
		
		/**
		 * G�n�re une exception AlgorithmFailedExeption
		 *
		 * @param s le message � associer � l'exception
		 */
		public AlgorithmFailedExeption(String s) {
			super(s);
		}
	}
}
