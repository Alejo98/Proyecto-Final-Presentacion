/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegos;

import java.util.Calendar;

/**
 *
 * @author Felipe
 */
public class ThreadTiempo implements Runnable {
    private Tuberia tuberia;
    int remiendo;
private boolean time;

    public ThreadTiempo(Tuberia tuberia) {
        this.tuberia = tuberia;
    }

    @Override
    
    public void run() {
        long T_inicial =Calendar.getInstance().getTimeInMillis();
        long Auxiliar=0;
        time=true;
        while(time){
        long tiempo=Calendar.getInstance().getTimeInMillis();
                try {
			Thread.sleep(2);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
        Auxiliar=tiempo-T_inicial;
        
        remiendo=(int) Auxiliar;
        
        tuberia.prueba(remiendo);
        remiendo =tuberia.getPruebiña();
        System.out.println(remiendo);
        }
        
        System.out.println("El tiempo de ejecucion fue"+(Auxiliar-1000));
    }

    
    
    public synchronized void estado(boolean est){
        time=est;
        System.out.println("SI EJECUTA ESTADO");
    }
    
    
}
    
    

