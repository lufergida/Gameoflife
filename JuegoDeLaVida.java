package org.example;

import java.util.Random;

public class JuegoDeLaVida {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Debe proporcionar 3 parámetros: w, h y p.");
            return;
        }

        int w = Integer.parseInt(args[0]); // Ancho
        int h = Integer.parseInt(args[1]); // Alto
        double p = Double.parseDouble(args[2]); // Probabilidad de células vivas en la población inicial

        int[][] generacionActual = crearGeneracionInicial(w, h, p);

        int s = 1000; // Tiempo de espera en milisegundos
        int g = 100; // generaciones

        int contadorGeneraciones = 0;
        while (contadorGeneraciones < g) {
            System.out.println("Generación " + contadorGeneraciones + ":");
            mostrarGeneracion(generacionActual);
            generacionActual = calcularSiguienteGeneracion(generacionActual);
            try {
                Thread.sleep(s); //Tiempo de espera basado en s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            contadorGeneraciones++;
        }
    }
    public static int[][] crearGeneracionInicial(int w, int h, double p) { // se crea la matriz y se genera los nùmeros aleatorios
        int[][] generacion = new int[h][w];
        Random random = new Random();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                generacion[i][j] = (random.nextDouble() < p) ? 1 : 0;
            }
        }

        return generacion;
    }

    public static void mostrarGeneracion(int[][] generacion) { //para mostrar estado cèlula
        for (int i = 0; i < generacion.length; i++) {
            for (int j = 0; j < generacion[0].length; j++) {
                System.out.print(generacion[i][j] == 1 ? "O " : ". ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public static int contarVecinasVivas(int[][] generacion, int fila, int columna) {
        int count = 0;
        int h = generacion.length;
        int w = generacion[0].length;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int vecinaFila = (fila + i + h) % h;
                int vecinaColumna = (columna + j + w) % w;
                count += generacion[vecinaFila][vecinaColumna];
            }
        }

        count -= generacion[fila][columna];
        return count;
    }
    public static int[][] calcularSiguienteGeneracion(int[][] generacionActual) {
        int h = generacionActual.length;
        int w = generacionActual[0].length;
        int[][] siguienteGeneracion = new int[h][w];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int vecinasVivas = contarVecinasVivas(generacionActual, i, j);
                siguienteGeneracion[i][j] = calcularSiguienteEstado(generacionActual[i][j], vecinasVivas);
            }
        }

        return siguienteGeneracion;
    }



    public static int calcularSiguienteEstado(int estadoActual, int vecinasVivas) { //ya implementaciòn de las reglas
        if (estadoActual == 1 && (vecinasVivas < 2 || vecinasVivas > 3)) {
            return 0; // muere
        } else if (estadoActual == 0 && vecinasVivas == 3) {
            return 1; // nace
        } else {
            return estadoActual; //estado actual
        }
    }
}
