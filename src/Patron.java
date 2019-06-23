
/**
 *
 * @author Cristhian
 */
public class Patron {
  public  int[][] patronCodigo;
    String valorReferencia;

    public Patron(int[][] patron, String valorDePatron) {
        patronCodigo=patron;
        valorReferencia= valorDePatron;
    }

    @Override
    public boolean equals( Object obj) {
        return Matriz.equals(patronCodigo, (int[][]) obj);
    }

    public int coincidencias(int[][] patronMultiplicar) {
        int contador=0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < patronCodigo[0].length; j++) {
                if (patronCodigo[i][j]==patronMultiplicar[i][j] && patronCodigo[i][j]==1){
                    contador++;
                }
            }
        }

        return contador;
    }
}
