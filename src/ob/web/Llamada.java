/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ob.web;

import java.io.PrintWriter;
import java.util.Date;
import ob.printer.Imprimir;

/**
 *
 * @author jc
 */
public class Llamada {
    
    public void llamar(String url, PrintWriter out) {
        System.out.println(url);
        boolean success = true;
        url = url.substring(1);
        String[] modulo = url.split("/");
        for (String mod : modulo) {
            System.out.println(mod);
        }
        
        switch(modulo[1]) {
            case "precuenta":
                System.out.println("PRECUENTA");
                System.out.println(modulo[2]);
                System.out.println(modulo[3]);
                success = new Imprimir().precuenta(Integer.parseInt(modulo[2]), modulo[3]);
                break;
            case "factura":
                System.out.println("FACTURA");
                System.out.println(modulo[2]);
                success = new Imprimir().factura(Integer.parseInt(modulo[2]));
                break;
            case "pedido":
                System.out.println("PEDIDO");
                if(modulo[2].equals("liberar")) {
                    System.out.println("LIBERAR");
                    System.out.println(modulo[3]);
                    success = new Imprimir().liberar(Integer.parseInt(modulo[3]));
                } else {
                    if(modulo.length==4) {
                        System.out.println(modulo[2]);
                        System.out.println(modulo[3]);
                        success = new Imprimir().enviarPedido(Integer.parseInt(modulo[2]), modulo[3]);
                    } else if(modulo.length==5) {
                        System.out.println(modulo[2]);
                        System.out.println(modulo[3]);
                        System.out.println(modulo[4]);
                        success = new Imprimir().editarEnvio(modulo[2], Integer.parseInt(modulo[3]), Integer.parseInt(modulo[4]));
                    }
                }
                break;
            case "cierre":
                System.out.println("CIERRE");
                if(modulo.length==3) {
                    System.out.println(modulo[2]);
                    success = new Imprimir().cierre(Integer.parseInt(modulo[2]), 0);
                } else if(modulo.length==4) {
                    System.out.println(modulo[2]);
                    System.out.println(modulo[3]);
                    success = new Imprimir().cierre(Integer.parseInt(modulo[2]), Integer.parseInt(modulo[3]));
                }
                
                break;
        }
        
        retorno(out, success);
    }
    
    private void retorno(PrintWriter out, boolean success) {
        try {
            out.println("HTTP/1.1 200 OK");
            out.println("Server: JC Server/1.0");
            out.println("Date: " + new Date());
            out.println("Access-Control-Allow-Origin: *");
            out.println("Content-Type: application/json;charset=utf-8");
            
            //out.println("Content-Length: " + mifichero.length());
            out.println("\n");
            out.println("{success: "+success+"}");
            out.close();

        } catch (Exception e) {
            e.getMessage();
        }
    }
}
