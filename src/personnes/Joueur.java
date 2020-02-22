/**
 * @file Joueur.java
 * @brief classe Joueur du projet Crazy Circus et exceptions relatives
 * @version 1.0 - 02.03.2019
 * @author PINARD Lucas & Amiot Tanguy
 */

package personnes;

/** Classe Joueur */
public class Joueur {
	
	/** constante MAX_NOM définit la longueur maximale du nom. */
	private static final int MAX_NOM = 20;

	/** Le nom. */
	private String nom;
	
	/** Le score. */
	private int score;
	
	/** Définit si le joueur peut jouer. */
	private boolean peutJouer;

	/**
	 * Construit un joueur avec un nom spécifié et un score nul.
	 *
	 * @param nom : Le nom du joueur à construire
	 * @throws NameTooLongException si le nom est supérieur à la limite de caractères
	 */
	public Joueur(String nom) throws NameTooLongException {
		if (nom.length() > MAX_NOM) {
			throw new NameTooLongException("Le nom saisi est trop long.");
		}
		this.nom = nom.replaceAll(" ", "").toLowerCase();
		score = 0;
		peutJouer = true;
	}

	/**
	 * Construit un String représentant le nom du Joueur et son score.
	 *
	 * @return un String de 25 caractères construit de la sorte :
	 * 			<br>(de gauche à droite)
	 * 			<br>-le score du joueur, justifié à gauche sur 5 caractères
	 * 			<br>-le nom du joueur, justifié à droite sur 20 caractères
	 */
	@Override
	public String toString() {
		return String.format("%-5d%20s", score, nom);
	}

	/**
	 * Récupère le nom.
	 *
	 * @return le nom du joueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Récupère le score.
	 *
	 * @return le score du joueur
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Renvoie si le joueur peut jouer.
	 *
	 * @return true si le joueur peut jouer, false sinon
	 */
	public boolean peutJouer() {
		return peutJouer;
	}

	/**
	 * Incrémente d'un point le score du joueur.
	 */
	public void incrémenteScore() {
		score++;
	}

	/**
	 * Inverse le statut du joueur (peut jouer/ne peut pas jouer).
	 */
	public void aJoué() {
		peutJouer = !peutJouer;
	}
	
	/** Exception NameTooLongException */
	public class NameTooLongException extends Exception{
		
		/**
		 * Génère une exception NameTooLongException 
		 * @param s le message à lier à l'exception
		 */
		public NameTooLongException(String s) {
			super(s);
		}
	}
}
