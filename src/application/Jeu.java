/**
 * @file Jeu.java
 * @brief classe principale du projet Crazy Circus
 * @version 1.0 - 05.03.2019
 * @author PINARD Lucas & AMIOT Tanguy
 */
package application;

import java.util.LinkedList;
import java.util.Scanner;

import cartes.*;
import cartes.CarteObjectif.ImpossibleMoveException;
import cartes.Deck.AlgorithmFailedExeption;
import personnes.*;
import personnes.Joueur.NameTooLongException;
import personnes.Joueurs.PlayerAlreadyExistsException;


public class Jeu {
	
	/** Les joueurs de la partie. */
	private Joueurs joueurs;
	
	/** Le deck de la partie. */
	private Deck deck;
	
	/** La carte courante actuelle. */
	private CarteObjectif courante;
	
	/** La carte objectif actuelle */
	private CarteObjectif objectif;
	
	/**
	 * Génère un nouveau jeu
	 */
	public Jeu() {
		Scanner sc = new Scanner(System.in);
		joueurs = new Joueurs();
		inscrireJoueurs(sc);
		try {
			deck = new Deck();
		} catch (AlgorithmFailedExeption e) {
			System.err.println(e.getMessage());
		}
		jouer(sc);
		sc.close();
	}
	
    /**
     * Inscrire les joueurs.
     *
     * @param sc - Le Scanner associé au jeu
     * @return La liste de joueurs
     */
    private void inscrireJoueurs(Scanner sc) {
	do {
		System.out.println("\n\n\n"
			+ "Inscription des joueurs :\n"
			+ joueurs.toString() + "\n"
			+ "Pour ajouter un joueur, entrez son nom.\n"
			+ "Pour supprimer un joueur, entrez à nouveau son nom.\n"
			+ "Pour terminer et lancer la partie, appuyez sur entrée");
		String buffer = new String(sc.nextLine());
		if (buffer.isEmpty())
				if (joueurs.pouvantJouer().size() >= 2)
					break;
				else {
					inscrireJoueurs(sc);
					return;
				}
		else
			if (joueurs.exists(buffer))
				joueurs.removeJoueur(buffer);
			else
				try {
					joueurs.addJoueur(buffer);
} catch (NameTooLongException | PlayerAlreadyExistsException e) {
					System.out.println(e.getMessage());
				}
	} while (true);
}

	/**
	 * Suis le déroulement de la partie.
	 *
	 * @param sc - Le scanner associé au jeu
	 */
	private void jouer(Scanner sc) {
		objectif = deck.tirerCarte();
		while (!deck.estVide()) {
			joueurs.resetPeutJouer();
			courante = objectif;
			objectif = deck.tirerCarte();
			
			System.out.println("\n" + objectif.toString(courante));
			
			String commande;
			do {
				if (joueurs.pouvantJouer().size() <= 1) {
			joueurs.getJoueur(joueurs.pouvantJouer().getFirst()).incrémenteScore();
			System.out.println(joueurs.pouvantJouer().getFirst()
				+ " gagne en étant dernier pouvant jouer!");
				break;
				}
				commande = new String(sc.nextLine());
			} while(!interpreterCommande(commande));
		}
		System.out.println("\nFIN DU JEU\n" + joueurs.afficherScores());
	}

	/**
	 * Interpréter une commande en entrée.
	 *
	 * @param cmd - La commande à interpréter
	 * @return true si la commande à été interprétée correctement, false sinon
	 */
	private boolean interpreterCommande(String cmd) {
		String nom = cmd.split(" ")[0];
		String mvs;
		try {
			mvs = cmd.split(" ")[1];
		} catch (Exception ArrayIndexOutOfBoundsExeption) {
			System.out.println("Commande invalide!");
			return false;
		}
		
		if (!joueurs.exists(nom)) {
			System.out.println("Ce joueur n'existe pas!");
			return false;
		}
		
		if (!joueurs.getJoueur(nom).peutJouer()) {
			System.out.println("Ce joueur a déjà joué!");
			return false;
		}
		
		LinkedList<Mouvement> mvsList = new LinkedList<Mouvement>();
		while(!mvs.isEmpty()) {
			try {
			mvsList.add(Mouvement.valueOf(mvs.substring(0,2)));
			} catch(Exception IllegalArgumentException) {
				System.out.println("Commande invalide!");
				return false;
			}
			mvs = mvs.replaceFirst(mvs.substring(0, 2), "");
		}
		
		boolean estTrouvé;
		try {
			estTrouvé = courante.effectuerMouvements(mvsList, objectif);
		} catch (ImpossibleMoveException e) {
			System.out.println("Suite de mouvements impossible!");
			joueurs.getJoueur(nom).aJoué();
			return false;
		}
		
		if (estTrouvé) {
			joueurs.getJoueur(nom).incrémenteScore();
			System.out.println("Bonne réponse!");
			return true;
		} 
		else {
			joueurs.getJoueur(nom).aJoué();
			System.out.println("Mauvaise réponse !");
			return false;
		}
	}
}
