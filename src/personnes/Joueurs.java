/**
 * @file Joueur.java
 * @brief classe Joueur du projet Crazy Circus et exceptions relatives
 * @version 1.0 - 02.03.2019
 * @author PINARD Lucas & Amiot Tanguy
 */

package personnes;

import java.util.LinkedList;

import personnes.Joueur.NameTooLongException;

/** Classe Joueurs */
public class Joueurs {

	/** Liste des joueurs. */
	private LinkedList<Joueur> liste;
	
	/**
	 * Construit une liste de joueurs vide.
	 */
	public Joueurs() {
		liste = new LinkedList<Joueur>();
	}
	
	/**
	 * Construit un String représentant la liste de joueurs.
	 *
	 * @return un String représentant la liste des noms des joueurs
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Joueur i : liste)
			sb.append(i.getNom() + "\n");
		return sb.toString();
	}

	/**
	 * Récupère le nom du joueur.
	 *
	 * @param nom : Le nom à rechercher dans la liste
	 * @return le joueur de la liste avec le nom spécifié, null s'il n'existe pas
	 */
	public Joueur getJoueur(String nom) {
		for (Joueur i : liste)
			if (i.getNom().equals(nom.toLowerCase())) return i;
		return null;
	}

	/**
	 * Renvoie si un joueur avec ce nom existe.
	 *
	 * @param nom : Le nom à rechercher dans la liste
	 * @return true si ce joueur est dans la liste, false sinon
	 */
	public boolean exists(String nom) {
		return (getJoueur(nom) != null);
	}
	
	/**
	 * Ajoute un joueur à la liste, défini par son nom.
	 *
	 * @param nom : le nom du joueur à ajouter
	 * @throws NameTooLongException si le nom saisi est supérieur à la limite de caractères
	 * @throws PlayerAlreadyExistsException si le nom est déja attribué à un Joueur existant dans la liste
	 */
	public void addJoueur(String nom) 
			throws NameTooLongException, PlayerAlreadyExistsException {
		if(exists(nom))
			throw new PlayerAlreadyExistsException("Le joueur est déja dans la liste.");
		liste.add(new Joueur(nom));
	}
	
	/**
	 * Retire un joueur de la liste, recherché par son nom.
	 *
	 * @param nom the nom
	 */
	public void removeJoueur(String nom) {
		liste.remove(getJoueur(nom.toLowerCase()));
	}
	
	/**
	 * Récupère la liste des joueurs pouvant jouer.
	 *
	 * @return La liste des noms des joueurs pouvant jouer
	 */
	public LinkedList<String> pouvantJouer() {
		LinkedList<String> res = new LinkedList<String>();
		for (Joueur j : liste) {
			if (j.peutJouer()) {
				res.add(j.getNom());
			}
		}
		return res;
	}

	/**
	 * Remet le statut 'peutJouer' de chaque joueur à true.
	 */
	public void resetPeutJouer() {
		for(Joueur j : liste)
			if (!j.peutJouer())
				j.aJoué();
	}
	
	/**
	 * Trie la liste des joueurs, par scores décroissants (tri à bulles).
	 */
	private void trierJoueurs() {
		for (int i = 0; i < liste.size(); i++) {
			for (int j = liste.size() - 1; j > i; j--) {
				if (liste.get(j).getScore() > liste.get(j-1).getScore())
					liste.add(j - 1, liste.remove(j));
			}
		}
	}

	/**
	 * Afficher scores.
	 *
	 * @return un String formaté pour afficher la table des scores, affichés par scores décroissant
	 */
	public String afficherScores() {
		trierJoueurs();
		StringBuilder sb = new StringBuilder(
				  "SCORE              JOUEUR\n"
				  + "=========================\n");
		for(Joueur i : liste) {
			sb.append(i + "\n");
		}
		return sb.toString();
	}
	
	/** Exception PlayerAlreadyExistsException */
	public class PlayerAlreadyExistsException extends Exception{
		
		/**
		 * Génère une exception PlayerAlreadyExistsException
		 * @param s le message à lier à l'exception
		 */
		public PlayerAlreadyExistsException(String s) {
			super(s);
		}
	}
}
