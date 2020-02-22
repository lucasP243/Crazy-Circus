/**
 * @file CarteObjectif.java
 * @brief classe CarteObjectif du projet Crazy Circus et exceptions relatives
 * @version 1.0 - 04.03.2019
 * @author PINARD Lucas & Amiot Tanguy
 */
package cartes;

import java.util.LinkedList;

/** Classe CarteObjectif */
public class CarteObjectif implements Cloneable {
	
	/** Constante d�finisasnt la taille max d'un podium */
	private static final int TAILLE_PODIUM = 3;
	
	/** Podium bleu */
	private LinkedList<Animal> podiumBleu;

	/** Podium rouge */
	private LinkedList<Animal> podiumRouge;
	
	/**
	 * Construit une nouvelle carte objectif vierge
	 */
	public CarteObjectif() {
		podiumBleu = new LinkedList<Animal>();
		podiumRouge = new LinkedList<Animal>();
	}
	
	/**
	 * Construit une nouvelle carte objectif
	 * @param bleu - Le podium bleu de la carte
	 * @param rouge - Le podium rouge de la carte
	 * @throws PodiumTooLongException si les podiums en param�tres sont trop grand
	 */
	@SuppressWarnings("unchecked")
	public CarteObjectif(LinkedList<Animal> bleu, LinkedList<Animal> rouge)
		throws PodiumTooLongException {
		this();
		if (bleu.size() > TAILLE_PODIUM || rouge.size() > TAILLE_PODIUM) {
			throw new PodiumTooLongException("L'un des deux podiums est trop grand");
		}
		
		podiumBleu = (LinkedList<Animal>) bleu.clone();
		podiumRouge = (LinkedList<Animal>) rouge.clone();
	}

	/**
	 * Construit un String repr�sentant la CarteObjectif et sa carte courante
	 * @param carteCourante - la carte � afficher avec la carteObjectif
	 * @return un String repr�sentant la CarteObjectif et sa carte courante
	 */
	public String toString(CarteObjectif carteCourante) {
		StringBuilder sb = new StringBuilder();
		String smallTab = "   ", bigTab = "         ", 
				buffer;
		
		for (int i = TAILLE_PODIUM - 1; i >= 0; --i) {
			sb.append(smallTab);
			try {
				buffer = carteCourante.podiumBleu.get(i).name();
			} catch (Exception IndexOutOfBoundsException) {
				buffer = bigTab;
			} 
			sb.append(String.format("%9s", buffer) + smallTab);
			try {
				buffer = carteCourante.podiumRouge.get(i).name();
			} catch (Exception IndexOutOfBoundsException) {
				buffer = bigTab;
			}
			sb.append(String.format("%9s", buffer));
			sb.append(i == 1 ?
					smallTab + "-->" + smallTab : bigTab);
			try {
				buffer = this.podiumBleu.get(i).name();
			} catch (Exception IndexOutOfBoundsException) {
				buffer = bigTab;
			} 
			sb.append(String.format("%9s", buffer) + smallTab);
			try {
				buffer = this.podiumRouge.get(i).name();
			} catch (Exception IndexOutOfBoundsException) {
				buffer = bigTab;
			}
			sb.append(String.format("%9s", buffer) + "\n");
		}
		sb.append(smallTab + "---------" + smallTab + "---------"
				+ bigTab + "---------" + smallTab + "---------" + "\n");
		sb.append(smallTab + "     BLEU" + smallTab + "    ROUGE"
				+ bigTab + "     BLEU" + smallTab + "    ROUGE" + "\n");
		return sb.toString();
	}
	
	/**
	 * Clone la CarteObjectif.
	 * 
	 * @return un clone de la carte
	 */
	@Override
	public CarteObjectif clone() {
		CarteObjectif clone = new CarteObjectif();
		clone.podiumBleu = this.getPodiumBleu();
		clone.podiumRouge = this.getPodiumRouge();
		return clone;
	}

	/**
	 * R�cup�re le podium bleu.
	 * @return un clone du podium bleu
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<Animal> getPodiumBleu() {
		return (LinkedList<Animal>) podiumBleu.clone();
	}

	/**
	 * R�cup�re le podium rouge.
	 * @return un clone du podium rouge
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<Animal> getPodiumRouge() {
		return (LinkedList<Animal>) podiumRouge.clone();
	}

	/**
	 * V�rifie si la CarteObjectif est �gale � une autre.
	 * @param carte - la CarteObjectif � comparer
	 * @return true si les cartes sont �gales, false sinon
	 */
	public boolean estEgale(CarteObjectif carte) {
		return this.podiumBleu.equals(carte.podiumBleu) && 
				this.podiumRouge.equals(carte.podiumRouge);
	}
	
	/**
	 * V�rifie si un mouvement est possible sur la CarteObjectif.
	 * @param m - le mouvement � v�rifier
	 * @return true si le mouvement est possible, false sinon
	 */
	public boolean estPossible(Mouvement m) {
		switch (m) {
		case KI:
			return !podiumBleu.isEmpty();
		case LO:
			return !podiumRouge.isEmpty();
		case SO:
			return !(podiumBleu.isEmpty() || podiumRouge.isEmpty());
		case NI:
			return podiumBleu.size() > 1;
		case MA:
			return podiumRouge.size() > 1;
		default:
			return false;
		}
	}

	/**
	 * D�finit le podium bleu.
	 * @param podium - le podium bleu � d�finir
	 * @throws PodiumTooLongException si le podium est trop grand
	 */
	@SuppressWarnings("unchecked")
	public void setPodiumBleu(LinkedList<Animal> podium) throws PodiumTooLongException {
		if (podium.size() > TAILLE_PODIUM) {
			throw new PodiumTooLongException("Le podium est trop grand.");
		}
		podiumBleu = (LinkedList<Animal>) podium.clone();
	}

	/**
	 * D�finit le podium rouge.
	 * @param podium - le podium rouge � d�finir
	 * @throws PodiumTooLongException si le podium est trop grand
	 */
	@SuppressWarnings("unchecked")
	public void setPodiumRouge(LinkedList<Animal> podium) throws PodiumTooLongException {
		if (podium.size() > TAILLE_PODIUM) {
			throw new PodiumTooLongException("Le podium est trop grand.");
		}
		podiumRouge = (LinkedList<Animal>) podium.clone();
	}

	/**
	 * V�rifie si d'une suite de mouvements appliqu� � la CarteObjectif r�sulte une autre CarteObjectif.
	 * @param mouvements - la liste ordonn�e des mouvements � effectuer
	 * @param objectif - la CarteObjectif � comparer
	 * @return true si la suite de mouvement donne la bonne CarteObjectif, false sinon
	 * @throws ImpossibleMoveException si l'un des mouvements est impossible � effectuer
	 */
	public boolean effectuerMouvements(LinkedList<Mouvement> mouvements, CarteObjectif objectif)
			throws ImpossibleMoveException {
		CarteObjectif r�sultante = this.clone();
		while(!mouvements.isEmpty()) {
			Mouvement mv = mouvements.removeFirst();
			if (!r�sultante.estPossible(mv)) {
				throw new ImpossibleMoveException("Le mouvement est infaisable.");
			}
			switch (mv) {
			case KI:
				r�sultante.podiumRouge.add(r�sultante.podiumBleu.removeLast());
				break;
			case LO:
				r�sultante.podiumBleu.add(r�sultante.podiumRouge.removeLast());
				break;
			case SO:
				Animal temp = r�sultante.podiumBleu.removeLast();
				r�sultante.podiumBleu.add(r�sultante.podiumRouge.removeLast());
				r�sultante.podiumRouge.add(temp);
				break;
			case NI:
				r�sultante.podiumBleu.add(r�sultante.podiumBleu.removeFirst());
				break;
			case MA:
				r�sultante.podiumRouge.add(r�sultante.podiumRouge.removeFirst());
			}
		}
		return r�sultante.estEgale(objectif);
	}
	
	/** Exception PodiumTooLongException */
	public class PodiumTooLongException extends Exception {
		
		/**
		 * G�n�re une exception PodiumTooLongException
		 * @param s - le message � associer � l'exception
		 */
		public PodiumTooLongException(String s) {
			super(s);
		}
	}

	/** Exception ImpossibleMoveException */
	public class ImpossibleMoveException extends Exception {
		
		/**
		 * G�n�re une exception ImpossibleMoveException
		 * @param s - le message � associer � l'exception
		 */
		public ImpossibleMoveException(String s) {
			super(s);
		}
	}
}
