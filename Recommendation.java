package assignment;

import java.util.Random;

public class Recommendation {

	/*
	 * Inscrivez votre nom complet (pr�nom et nom de famille)
	 * ainsi que votre num�ro sciper ci-dessous :
	 */
	
	/* Etudiant 1 */
	public static String NAME1 = "Jean S�rien";
	public static int SCIPER1 = 123456;
	
	/* Etudiant 2 - laissez tel quel si vous avez cod� le projet tout seul */
	public static String NAME2 = "Anne Onyme";
	public static int SCIPER2 = 123789;
	
	static Random random = new Random();
	
	public static String matrixToString(double[][] A) {
		/* M�thode � coder */		
		return "";
	}
	
	public static boolean isMatrix( double[][] A ) {
		/* M�thode � coder */		
		return true;
	}
	
	public static double[][] multiplyMatrix(double[][] A, double[][] B) {
		/* M�thode � coder */
		return null;	
	}
	
	public static double[][] createMatrix( int n, int m, int k, int l) {
		/* M�thode � coder */
		/* Utilisez la variable 'random', par exemple */
		int tmp = random.nextInt(5);
		return null;
	}
	
	public static double rmse(double[][] M, double[][] P) {
		/* M�thode � coder */	
		return 0;
	}
	
	public static double updateUElem( double[][] M, double[][] U, double[][] V, int r, int s ) {
		/* M�thode � coder */	
		return 0;
	}
	
	public static double updateVElem( double[][] M, double[][] U, double[][] V, int r, int s ) {
		/* M�thode � coder */	
		return 0;		
	}
	
	public static double[][] optimizeU( double[][] M, double[][] U, double[][] V) {
		/* M�thode � coder */	
		return null;		
	}

	public static double[][] optimizeV( double[][] M, double[][] U, double[][] V) {
		/* M�thode � coder */	
		return null;		
	}
	
	public static int[] recommend( double[][] M, int d) {
		/* M�thode � coder */	
		return null;
	}
}


