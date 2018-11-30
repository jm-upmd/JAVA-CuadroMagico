/**
 * Programa que genera un Cuadro Mágico de dimensiones NxN, donde N=4k
 * Utiliza el método de los 9 bloques.
 * Un Cuadro Mágico es aquel que cumple que la suma de los elementos de cada una 
 * de sus filas es identica y coincide con la suma de los elementos de cada una 
 * de sus columnas.
 * 
 * Ejemplo (para K=1):
 * 
 *  	 1    15    14     4
    	12     6     7     9
     	 8    10    11     5
    	13     3     2    16
    	
   Cada fila y cada columna suma 34
   
   Mas info en:
   http://www.ehu.eus/~mtpalezp/descargas/magiacuadrada.pdf
   (Página 15. Apartado: "Cuadrados de orden n = 4k)
   
 */
package cuadro_magico;

import java.util.Scanner;


public class CuadroMagico4k_v1 {

	// La dimensión de cuadro ha de ser N = 4K
	static final int DIMEN_BASE = 4;
	static int Dimen; // Representa el K, que pediremos por teclado
	static int[][] cuadroInicial;  // array bidimensional con valores del cuadro inicial
	static int[][] cuadroMagico;  // array bidimensional con el cuadro mágico

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		int dimen = 0;

		System.out.println("*** Generador de Cuadros Mágicos de dimensión NxN, con N=4K ***\n\n");
		System.out.println("Introduce el valor de K: ");

		
		// Pedimos por teclado el valor de K
		if (teclado.hasNextInt()) {
			dimen = teclado.nextInt();
			teclado.close();
			if (dimen <= 0) {
				System.out.println("El dato introducido no es un numero entero positivo mayor que cero");
				System.exit(0);
			}
		}

		dimen *= DIMEN_BASE; // dimensión del cuadro. cuadro es de dimen * dimen elementos.


		// Genera cuadro inicial con valores consecutivos 
		generaCuadroInicial(dimen);
		// muestraCuadro(cuadroInicial);

		
		
		int d4 = dimen / 4;  
		int d2 = dimen / 2;
		int d42 = d2 + d4 + 1;
		
		/* 
		 * Bloques A,C,G,I son de dimensiónes      d4 filas x d4 columnas
		 * Bloques B,H     son de dimensiones      d4 filas x d2 columnas 
		 * Bloques D,F     son de dimensiones      d2 filas x d4 columnas
		 * Bloque  E       es de dimensión 		   d2 files x d2 columnas
		 */
		
		// Creamos matriz para el cuadro mágico
		cuadroMagico = new int[dimen][dimen];

		
		// Copiamos bloques de cuadro inical al cuadro mágico.
		
		// Copia bloque que comieza in columna 1, fila 1, y tiene d4 filas y d4 columnas
		copiaBloque( 1, 1, d4, d4);  // Copia bloque que comieza in columna 1, fila 1, y tiene d4 filas y d4 columnas
		copiaBloque(d42, 1, d4, d4);  
		copiaBloque(d4 + 1, d4 + 1, d2, d2);
		copiaBloque(1, d42, d4, d4);
		copiaBloque(d42, d42, d4, d4);

		// Copia a cuadroMagico los bloques B,H,D,F simetricos respecto a cuadroInicial
		
		copiaBloqueSimetrico(d4 + 1, 1, d4, d2,d4 + 1, d42, d4, d2);  
		copiaBloqueSimetrico(d4 + 1, d42, d4, d2,d4 + 1, 1, d4, d2);
		copiaBloqueSimetrico(1, d4 + 1, d2, d4,d42, d4 + 1, d2, d4);
		copiaBloqueSimetrico(d42, d4 + 1, d2, d4,1, d4 + 1, d2, d4);

		muestraCuadro(cuadroMagico);

	}

	private static void generaCuadroInicial(int n) {
		cuadroInicial = new int[n][n];
		int contador = 1;
		for (int fil = 1; fil <= n; fil++)
			for (int col = 1; col <= n; col++)
				cuadroInicial[fil - 1][col - 1] = contador++;

	}

	// Otra forma de rellenar el cuadro con valores consecutivos
	private static void generaCuadroInicialV2(int n) {
		cuadroInicial = new int[n][n];
		for (int fil = 1; fil <= n; fil++)
			for (int col = 1; col <= n; col++)
				cuadroInicial[fil - 1][col - 1] = col + (n * (fil - 1));

	}

	private static void muestraCuadro(int[][] cuadro) {
		int n = cuadro[0].length;

		for (int fil = 0; fil < n; fil++) {

			System.out.println();
			for (int col = 0; col < n; col++) {
				System.out.printf("%6d", cuadro[fil][col]);
			}
		}
		System.out.println();

	}

	// Copia los elementos del bloque de cuadroOrigen a cuadroDestino
	private static void copiaBloque(int columna, int fila, int numFilas, int numColumnas) {

		for (int i = fila; i <= fila + numFilas - 1; i++)
			for (int j = columna; j <= columna + numColumnas - 1; j++)
				cuadroMagico[i - 1][j - 1] = cuadroInicial[i - 1][j - 1];

	}

	// Copia los elementos en orden simétrico del bloque de cuadroOrigen a cuadroDestino
		// a	b                			 d	 c
		//          copia simetrica -->
		// c    d                			 b 	 a
	
	private static void copiaBloqueSimetrico(int colOrigen, int filaOrigen,  int numFilasOrigen,  int numColOrigen,
			int colDestino, int filaDestino, int numFilasDestino, int numColDestino) {
		int yDest = filaDestino - 1;
		int xDest = colDestino - 1;
		int yOrg = filaOrigen + numFilasOrigen - 2;
		int xOrg = colOrigen + numColOrigen - 2;
		for (int i = yOrg; i >= filaOrigen - 1; i--) {
			for (int j = xOrg; j >= colOrigen - 1; j--)
				cuadroMagico[yDest][xDest++] = cuadroInicial[i][j];
			yDest++;
			xDest = colDestino - 1;

		}

	}

}
