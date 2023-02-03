/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programación.Tema6.BilleteOculto;

import java.util.Random;

/**
 *
 * @author Juan Instituto
 */
public class Billete {
import java.util.Random;
public static void main(String[] args){
public class Main {
    int filas = 8;
    int columnas = 4;
    String[][] tabla = new String[filas][columnas];
    Random random = new Random();
    
    for (int i = 0; i < filas; i++) {
      for (int j = 0; j < columnas; j++) {
        tabla[i][j] = ".";
      }
    }

    int filaBillete = random.nextInt(filas);
    int columnaBillete = random.nextInt(columnas);
    tabla[filaBillete][columnaBillete] = "€";

    System.out.println("Coordenadas del billete de 20€:");
    for (int i = 0; i < filas; i++) {
      for (int j = 0; j < columnas; j++) {
        System.out.print("(" + i + "," + j + ") " + tabla[i][j] + " ");
      }
      System.out.println();
    }
  }
}

}
