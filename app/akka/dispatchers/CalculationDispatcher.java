package akka.dispatchers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actors.CalculationActor;
import akka.messages.CalculationMessage;
import akka.routing.RoundRobinRouter;
import models.Result;
import scala.concurrent.duration.Duration;
import system.AppContext;

import java.util.concurrent.TimeUnit;

public class CalculationDispatcher {

    public static ActorRef calculationActor = AppContext.actorSystem.actorOf(new Props(CalculationActor.class).withRouter(new RoundRobinRouter(4)), "CalculationActor" );

    private CalculationMessage message;

    public CalculationDispatcher(int iterations, String description){
        message = new CalculationMessage(iterations, description);
    }

    public void doCalculation(){
        this.message.resultID = createAndReturnResult(CallType.NORMAL).id;

        calculationActor.tell(this.message, calculationActor);
    }

    public void doDelayedCalculation(Long delay){
        this.message.resultID = createAndReturnResult(CallType.DELAYED).id;

        AppContext.actorSystem.scheduler().scheduleOnce(Duration.create(delay, TimeUnit.SECONDS), calculationActor,this.message,AppContext.actorSystem.dispatcher());
    }


    private Result createAndReturnResult(CallType callType){
        // Create a calculationResult Record:
        Result result = new Result();
        result.calledBy = callType.name();
        result.save();

        /** I am calling this to ensure that the result
         * record is persisted in the database before
         * we call the actor.
         *
         * This is because I believe the actor runs in
         * a different context. I am not sure on the
         * workings of JPAPlugin, so if you have some advice
         * then please share it with me and I will correct the code.
         * */
        result.refresh();

        return result;
    }

    private enum CallType {
        NORMAL, DELAYED;
    }
}
