/**
 * @file Joueur.java
 * @brief classe Joueur du projet Crazy Circus et exceptions relatives
 * @version 1.0 - 02.03.2019
 * @author PINARD Lucas & Amiot Tanguy
 */

package personnes;

/** Classe Joueur */
public class Joueur {
	
	/** constante MAX_NOM d�finit la longueur maximale du nom. */
	private static final int MAX_NOM = 20;

	/** Le nom. */
	private String nom;
	
	/** Le score. */
	private int score;
	
	/** D�finit si le joueur peut jouer. */
	private boolean peutJouer;

	/**
	 * Construit un joueur avec un nom sp�cifi� et un score nul.
	 *
	 * @param nom : Le nom du joueur � construire
	 * @throws NameTooLongException si le nom est sup�rieur � la limite de caract�res
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
	 * Construit un String repr�sentant le nom du Joueur et son score.
	 *
	 * @return un String de 25 caract�res construit de la sorte :
	 * 			<br>(de gauche � droite)
	 * 			<br>-le score du joueur, justifi� � gauche sur 5 caract�res
	 * 			<br>-le nom du joueur, justifi� � droite sur 20 caract�res
	 */
	@Override
	public String toString() {
		return String.format("%-5d%20s", score, nom);
	}

	/**
	 * R�cup�re le nom.
	 *
	 * @return le nom du joueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * R�cup�re le score.
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
	 * Incr�mente d'un point le score du joueur.
	 */
	public void incr�menteScore() {
		score++;
	}

	/**
	 * Inverse le statut du joueur (peut jouer/ne peut pas jouer).
	 */
	public void aJou�() {
		peutJouer = !peutJouer;
	}
	
	/** Exception NameTooLongException */
	public class NameTooLongException extends Exception{
		
		/**
		 * G�n�re une exception NameTooLongException 
		 * @param s le message � lier � l'exception
		 */
		public NameTooLongException(String s) {
			super(s);
		}
	}
}
