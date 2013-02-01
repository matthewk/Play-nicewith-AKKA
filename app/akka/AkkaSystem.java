package akka;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.Play;

public class AkkaSystem {
        public ActorSystem getActorSystem() {
            Config config = ConfigFactory.parseFile(Play.getFile("conf/reference.conf"));
            return ActorSystem.create("TestSystem", config);
        }
}
