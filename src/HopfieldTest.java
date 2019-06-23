
import org.junit.Test;

public class HopfieldTest {

    Hopfield pruebas = new Hopfield(4, false);

    @Test
    public void verMatrizPatron() throws Exception {
        Hopfield redHopfield = new Hopfield(4, true);

        redHopfield.obtenerPatron("m1.png", 2, 2);

        int[][] m1 = redHopfield.obtenerPatronLineal("m1.png", 2, 2);

        Matriz.imprimir(m1);
    }

    @Test
    public void paso1EntrenamientoBase() throws Exception {
        pruebas = new Hopfield(4, false);
        pruebas.entrenar("m1.png", 2, 2, "patron1");
        Matriz.imprimir(pruebas.PesosTotal);
        System.out.println("/*****************/");
        pruebas.entrenar("m2.png", 2, 2, "patron2");
        Matriz.imprimir(pruebas.PesosTotal);
    }

    @Test
    public void paso2MostrarPatronNuevo() throws Exception {
        paso1EntrenamientoBase();
        //*******************************************************//
        pruebas.calcularIteraciones("m1.png",2,2);

    }



}
