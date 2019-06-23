
import java.util.Arrays;

/**
 *
 * @author Cristhian
 */
public class Matriz {
    
    // Imprimir una matriz por pantalla
    public static void imprimir(int[][] matriz) {
        for (int x = 0; x < matriz.length; x++) {
            System.out.print("|");
            for (int y = 0; y < matriz[x].length; y++) {
                System.out.print(matriz[x][y]);
                if (y != matriz[x].length - 1) System.out.print("\t");
            }
            System.out.println("|");
        }
    }

    // Transponer una matriz. Es decir, convertir sus filas en columnas.
    public static int[][] transponer(int[][] matriz) {

        int[][] nuevaMatriz = new int[matriz[0].length][matriz.length];

        for (int x = 0; x < matriz.length; x++) {
            for (int y = 0; y < matriz[x].length; y++) {
                nuevaMatriz[y][x] = matriz[x][y];
            }
        }

        return nuevaMatriz;

    }

    // Igualdad de Matrices
    public static boolean equals(int[][] m1, int[][] m2) {
        if (Arrays.deepEquals(m1, m2))
            return true;
        else
            return false;

    }

    // Restar Matrices
    public static int[][] resta(int[][] m1, int[][] m2) {

        int[][] resultado = new int[m1.length][m2[0].length];

        for (int x = 0; x < resultado.length; x++) {
            for (int y = 0; y < resultado[x].length; y++) {
                resultado[x][y] = m1[x][y] - m2[x][y];
            }
        }
        return resultado;
    }

    // Suma Matrices
    public static int[][] sumar(int[][] m1, int[][] m2) {

        int[][] resultado = new int[m1.length][m2[0].length];

        for (int x = 0; x < resultado.length; x++) {
            for (int y = 0; y < resultado[x].length; y++) {
                resultado[x][y] = m1[x][y] + m2[x][y];
            }
        }
        return resultado;
    }

    // Multiplica Matrices
    public static int[][] multiplicar(int[][] m1, int[][] m2) {

        int fil_m1 = m1.length;
        int col_m1 = m1[0].length;

        int fil_m2 = m2.length;
        int col_m2 = m2[0].length;

        if (col_m1 != fil_m2)
            throw new RuntimeException("No se pueden multiplicar las matrices");

        // La nueva matriz es de filas de M1 y columnas de M2
        int[][] multiplicacion = new int[fil_m1][col_m2];

        for (int x = 0; x < multiplicacion.length; x++) {
            for (int y = 0; y < multiplicacion[x].length; y++) {

                // El nuevo bucle suma la multiplicaci�n de la fila por la columna
                for (int z = 0; z < col_m1; z++) {
                    multiplicacion[x][y] += m1[x][z] * m2[z][y];
                }
            }
        }

        return multiplicacion;

    }

    public static int[][] identidad(int numero) {
        int[][] mIdentidad = new int[numero][numero];
        for (int i = 0; i < numero; i++) {
            for (int j = 0; j < numero; j++) {
                mIdentidad[i][j] = (i == j ? 1 : 0);
            }
        }
        return mIdentidad;
    }

    public static int[][] aplicarEscalon(int[][] matriz, int[][] patronAnterior) {
        for (int y = 0; y < matriz[0].length; y++) {
            matriz[0][y] = (matriz[0][y] == 0 ? patronAnterior[0][y] : (matriz[0][y] > 0 ? 1 : -1));
        }
        return matriz;
    }

    public static int productolineal(int[][] m1,int[][] m2){
        int suma=0;
        for (int y = 0; y < m1[0].length; y++) {
                suma+=m1[0][y]*m2[0][y];
        }
        System.out.println("La suma es: "+suma);
        return suma;
    }

    public static boolean sonOrtogonales(int[][] m1,int[][] m2){
        int valor=productolineal(m1,m2);
        return (valor==0?true:false);
    }
    
    public static int[][] deVectoraMatriz(int[][] m1,int filas,int columnas){
        int[][] devolver=new int[filas][columnas];
        for (int i = 0; i < filas; i++) 
            for (int j = 0; j < columnas; j++) 
                devolver[i][j]=m1[0][j+i*columnas];
        return devolver;
    }
}
