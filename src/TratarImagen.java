
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 *
 * @author Cristhian
 */
public class TratarImagen {

    public static void dividirImagen(String imagenPathInicial, int filas, int columnas) throws IOException {
        BufferedImage bi = ImageIO.read(new File(imagenPathInicial));
        Image img = bi.getScaledInstance(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
        BufferedImage[] imgs = SplitImage.getImages(img, filas, columnas);
        int finalCadena = imagenPathInicial.lastIndexOf(".");
        String cadena = imagenPathInicial.substring(0, finalCadena);
        new File(cadena).mkdirs();
        for (int i = 0; i < imgs.length; i++) {
            ImageIO.write(imgs[i], "jpg", new File(cadena+"/img" + i + ".jpg"));
        }
    }

    public static void convertirGris(String imagenPathInicial) throws IOException {
        File file = new File(imagenPathInicial);
        BufferedImage orginalImage = ImageIO.read(file);

        BufferedImage blackAndWhiteImg = new BufferedImage(
                orginalImage.getWidth(), orginalImage.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D graphics = blackAndWhiteImg.createGraphics();
        graphics.drawImage(orginalImage, 0, 0, null);
        int finalCadena = imagenPathInicial.lastIndexOf(".");
        String cadena = imagenPathInicial.substring(0, finalCadena)+"gris.png";
        ImageIO.write(blackAndWhiteImg, "png", new File(cadena));
    }

    public static int[][]  crearMatriz(int filas, int columnas, String pathCarpeta) throws IOException{
        int[][] matriz = new int[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                String prueba = String.format(pathCarpeta +"img%s.jpg", i * columnas + j);
                matriz[i][j] = (colorPredominante(prueba) == "Negro" ? 1 : -1);
            }
        }
       return   matriz;
    }

    private static String colorPredominante(String imagenPath) throws IOException {
        File file = new File(imagenPath);
        ImageInputStream is = ImageIO.createImageInputStream(file);
        Iterator iter = ImageIO.getImageReaders(is);
        if (!iter.hasNext()) {
            System.out.println("Cannot load the specified file " + file);
            System.exit(1);
        }
        ImageReader imageReader = (ImageReader) iter.next();
        imageReader.setInput(is);
        BufferedImage image = imageReader.read(0);
        int height = image.getHeight();
        int width = image.getWidth();
        Map m = new HashMap();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                Integer counter = (Integer) m.get(rgb);
                if (counter == null) counter = 0;
                counter++;
                m.put(rgb, counter);
            }
        }
        String colourHex = getColorNegro(m, 0.3);
        return colourHex;
    }

    private static String getColorNegro(Map map, double porcentajeReconocimiento) {
        Iterator it = map.keySet().iterator();
        Integer contadorBlanco = 0;
        Integer contadorNegro = 0;
        while (it.hasNext()) {
            Integer key = (Integer) it.next();
            int[] rgb = getRGBArr(key);
            if (rgb[0] == 0 && rgb[1] == 0 && rgb[2] == 0)
                contadorNegro = (Integer) map.get(key);
            if (rgb[0] == 255 && rgb[1] == 255 && rgb[2] == 255)
                contadorBlanco = (Integer) map.get(key);
        }
        int total = contadorBlanco + contadorNegro;
        if (contadorNegro >= total * porcentajeReconocimiento)
            return "Negro";
        return "Blanco";
    }

    private static int[] getRGBArr(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red, green, blue};
    }

   public static int[][] crearMatrizLineal(int filas, int columnas, String pathCarpeta) throws IOException {
       int[][] matriz = new int[1][filas*columnas];
       for (int i = 0; i < filas; i++) {
           for (int j = 0; j < columnas; j++) {
               String prueba = String.format(pathCarpeta +"img%s.jpg", i * columnas + j);
               matriz[0][i*columnas+j] = (colorPredominante(prueba) == "Negro" ? 1 : -1);
           }
       }
       return   matriz;
    }
}


class SplitImage {

    public static BufferedImage[] getImages(Image img, int rows, int column) {
        BufferedImage[] splittedImages = new BufferedImage[rows * column];
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img, 0, 0, null);
        int width = bi.getWidth();
        int height = bi.getHeight();
        int pos = 0;
        int swidth = width / column;
        int sheight = height / rows;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < column; j++) {
                BufferedImage bimg = bi.getSubimage(j * swidth, i * sheight, swidth, sheight);
                splittedImages[pos] = bimg;
                pos++;
            }
        }

        return splittedImages;
    }
}

