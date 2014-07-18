/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ob.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;
import ob.printer.util.Util;

/**
 *
 * @author jc
 */
public class PeticionWeb extends Thread {

    final int ERROR = 0;
    final int WARNING = 1;
    final int DEBUG = 2;

    private Socket scliente = null;		// representa la petición de nuestro cliente
    private PrintWriter out = null;		// representa el buffer donde escribimos la respuesta

    PeticionWeb(Socket ps) {
        scliente = ps;
        setPriority(NORM_PRIORITY - 1); // hacemos que la prioridad sea baja
    }

    @Override
    public void run() {// emplementamos el metodo run

        Util.info("Procesamos conexion");

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(scliente.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(scliente.getOutputStream(), "UTF-8"), true);

            String cadena;
            int i = 0;

            do {

                cadena = in.readLine();
                if (cadena != null) {
                    //sleep(500);
                    //depura("--" + cadena);
                }

                if (i == 0) { // la primera linea nos dice que fichero hay que descargar
                    i++;
                    StringTokenizer st = new StringTokenizer(cadena);

                    if ((st.countTokens() >= 2) && st.nextToken().equals("GET")) {
                        String token = st.nextToken();
                        if (!token.endsWith(".ico")) {
                            Util.info("Llamando " + token);
                            new Llamada().llamar(token, out);
                        }
                    } else {
                        out.println("400 Petición Incorrecta");
                    }
                }

            } while (cadena != null || !"".equals(cadena));
        } catch (IOException e) {
            Util.info("No encuentro el fichero " + e.getMessage());
            out.println("HTTP/1.1 400 OK");
            out.close();
        }

        Util.info("Hemos terminado");
    }

    private void retornaFichero() {
        try {
            out.println("HTTP/1.1 200 OK");
            out.println("Server: JC Server/1.0");
            out.println("Date: " + new Date());
            out.println("Content-Type: application/json;charset=utf-8");
            //out.println("Content-Length: " + mifichero.length());
            out.println("\n");

            out.println("{success: true}");

            Util.info("fin envio fichero");

            out.close();

        } catch (Exception e) {
            Util.info("Error al retornar fichero");
        }
    }
}
