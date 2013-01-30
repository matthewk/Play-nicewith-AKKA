package system;


import akka.AkkaSystem;
import akka.actor.ActorSystem;

public class AppContext {
    public static final ActorSystem actorSystem = new AkkaSystem().getActorSystem();
}
