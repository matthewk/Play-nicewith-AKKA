package akka.actors;


import akka.actor.UntypedActor;
import akka.messages.CalculationMessage;
import models.Result;
import play.Logger;
import play.db.jpa.JPAPlugin;

import java.util.Date;

public class CalculationActor extends UntypedActor{
    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof CalculationMessage){
            CalculationMessage message = (CalculationMessage) o;

            if (message.resultID != null){
                JPAPlugin.startTx(false); // starting a non-readonly transaction
                Result result = Result.findById(message.resultID);

                if (result !=  null){

                    result.description = message.description;
                    result.startedDate = new Date();
                    result.iterations = message.iterations;
                    result.value = doCalculation(message.iterations);
                    result.completedDate = new Date();
                    result.actorID = getSelf().path().name().toString();
                    result.save();
                    result.refresh();

                } else {
                    Logger.info("No result record was found for the id:" + message.resultID + ", check that the result record was actually saved.");
                }

                JPAPlugin.closeTx(false); // close the transaction. false means don't roll back
            } else {
                Logger.info("No result.id was provided and so no calculation will be performed");
            }

        } else {
            unhandled(o);
        }
    }

    private Long doCalculation(int iterations) throws InterruptedException {
        long result = 0L;
        int i = 0;
        while(i < iterations){
            result = result + 2L;
            i ++;
            Thread.sleep(10);
        }
        return Long.valueOf(result);
    }
}
