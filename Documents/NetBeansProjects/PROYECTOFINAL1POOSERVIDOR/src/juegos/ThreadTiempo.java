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
private boolean time;
    @Override
    public void run() {
        long T_inicial =Calendar.getInstance().getTimeInMillis();
        long Auxiliar=0;
        time=true;
        while(time){
        long tiempo=Calendar.getInstance().getTimeInMillis();
                try {
			Thread.sleep(1);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
        Auxiliar=tiempo-T_inicial;
       
        }
        
        System.out.println("El tiempo de ejecucion fue"+(Auxiliar-1000));
    }
    
    public void estado(boolean est){
        time=est;
    }
    
    
}
