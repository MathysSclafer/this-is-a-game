import java.util.TimerTask;
import java.util.Timer;

public class timerLimit {

    public static Timer beginTimer(){


        Timer timerTurn = new Timer();


        TimerTask stopTurn = new TimerTask() {

            @Override
            public void run() {
                System.out.println("Arrivé a la fin des 5 secondes");
                timerTurn.cancel();
            }
        };


        timerTurn.schedule(stopTurn, 5000);

        return timerTurn;




    }
}
