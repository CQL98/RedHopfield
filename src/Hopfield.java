
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Cristhian
 */
public class Hopfield {
    
    ArrayList<int[][]> PesosPatrones;
    public ArrayList<Patron> patrones;
    public int[][] PesosTotal;
    boolean verificarPatron = false;

    public Hopfield(int numeroNeuronas) {
        PesosPatrones = new ArrayList<>();
        patrones = new ArrayList<>();
    }

    public Hopfield(int numeroNeuronas, boolean verificarOrtogonal) {
        PesosPatrones = new ArrayList<>();
        patrones = new ArrayList<>();
        verificarPatron = verificarOrtogonal;
    }

    public void entrenar(String direccionPath, int filas, int columnas, String valorDePatron) throws Exception {
        int[][] patron = new int[filas][columnas];
        patron = obtenerPatronLineal(direccionPath, filas, columnas);
        if (verificarPatrones(patron, valorDePatron)) {
            int[][] PesosPatron = Matriz.resta(Matriz.multiplicar(Matriz.transponer(patron), patron), Matriz.identidad(filas * columnas));
            PesosPatrones.add(PesosPatron);
            patrones.add(new Patron(patron, valorDePatron));
            if (PesosTotal == null)
                PesosTotal = PesosPatron;
            else {
                PesosTotal = Matriz.sumar(PesosTotal, PesosPatron);
            }
        }

    }

    private boolean verificarPatrones(int[][] p, String valorDePatron) {
        if (!verificarPatron || patrones.isEmpty())
            return true;
        int maxPermitido = (int) (p[0].length * p.length * 0.3);
        for (Patron patron : patrones) {
            int x = Math.abs(Matriz.productolineal(p, patron.patronCodigo));
            if (x > maxPermitido) {
                System.out.println("imagen de patron: " + valorDePatron + " se parece mucho a:" + patron.valorReferencia);
                return false;
            }
        }
        return true;
    }

    public int[][] obtenerPatron(String direccionPath, int filas, int columnas) throws IOException {
        int finalCadena = direccionPath.lastIndexOf(".");
        String cadena = direccionPath.substring(0, finalCadena) + "gris";
        TratarImagen.convertirGris(direccionPath);
        TratarImagen.dividirImagen(cadena + ".png", filas, columnas);
        int[][] matriz = new int[filas][columnas];
        matriz = TratarImagen.crearMatriz(filas, columnas, cadena + "/");
        String cadenax = "";
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                cadenax += (matriz[i][j] == 1 ? " 1" : "-1") + " . ";
            }
            cadenax += "\n";
        }
        System.out.println(cadenax);
        return matriz;
    }

    public int[][] obtenerPatronLineal(String direccionPath, int filas, int columnas) throws IOException {
        int finalCadena = direccionPath.lastIndexOf(".");
        String cadena = direccionPath.substring(0, finalCadena) + "gris";
        TratarImagen.convertirGris(direccionPath);
        TratarImagen.dividirImagen(cadena + ".png", filas, columnas);
        int[][] matriz = new int[filas * columnas][1];
        matriz = TratarImagen.crearMatrizLineal(filas, columnas, cadena + "/");
        return matriz;
    }

    public void calcular(String direccionPath, int filas, int columnas) throws Exception {

        int[][] patron = new int[filas][columnas];
        patron = obtenerPatronLineal(direccionPath, filas, columnas);
        int[][] PatronMultiplicar = patron;
        boolean bandera = false;
        int[][] patronAnterior = patron;

        for (int i = 0; i < 30; i++) {
            PatronMultiplicar = Matriz.multiplicar(PatronMultiplicar, PesosTotal);
            //  if (i == 0)
            //  PatronMultiplicar = Matriz.aplicarEscalon(PatronMultiplicar);
            PatronMultiplicar = Matriz.aplicarEscalon(PatronMultiplicar, patronAnterior);

            patronAnterior = PatronMultiplicar;
            for (Patron p : patrones) {
                Matriz.imprimir(p.patronCodigo);
                if (p.equals(PatronMultiplicar)) {
                    System.out.println("el numero es " + p.valorReferencia);
                    bandera = true;
                    return;
                }
            }
            System.out.println("/***********************/");
            Matriz.imprimir(PatronMultiplicar);
            System.out.println("/***********************/");
        }
        if (bandera == false) {
            buscarElqueCoincideMas(PatronMultiplicar);
        }

    }

    public ArrayList<int[][]> calcularIteraciones(String direccionPath, int filas, int columnas) throws Exception {
        ArrayList<int[][]> patronesIteracion = new ArrayList<>();
        int[][] patron;
        patron = obtenerPatronLineal(direccionPath, filas, columnas);
        int[][] PatronMultiplicar = patron;
        int[][] patronAnterior = patron;
        int bandera = 0;
        patronesIteracion.add(patron);
        do {
            PatronMultiplicar = Matriz.multiplicar(PatronMultiplicar, PesosTotal);
            PatronMultiplicar = Matriz.aplicarEscalon(PatronMultiplicar, patronAnterior);
            patronAnterior = PatronMultiplicar;
            //Se vuelve 1 cuando se encuentra patron
            for (Patron p : patrones) {
                if (p.equals(PatronMultiplicar)) {
                    System.out.println("el patron coincide con:" + p.valorReferencia);
                    patronesIteracion.add(PatronMultiplicar);
                    return patronesIteracion;
                }
            }
            //se vuelve -1 estable se repite un bucle
            for (int[][] patronIteracion : patronesIteracion) {
                if (Matriz.equals(patronIteracion, PatronMultiplicar)) {
                    System.out.println("Red confundida bucle encontrado:");
                    bandera = -1;
                }
            }
            patronesIteracion.add(PatronMultiplicar);
        } while (bandera == 0);
        if (bandera == -1) {
            buscarElqueCoincideMas(PatronMultiplicar);
        }
        return patronesIteracion;
    }

    private String buscarElqueCoincideMas(int[][] PatronMultiplicar) {
        int inicio = 0;
        String x = "";
        for (Patron p : patrones) {

            Matriz.imprimir(p.patronCodigo);
            int coincidencias = p.coincidencias(PatronMultiplicar);
            if (inicio < coincidencias) {
                inicio = coincidencias;
                x = p.valorReferencia;
            }
        }
        System.out.println("Coincide mas :" + x);
        return x;

    }
}
