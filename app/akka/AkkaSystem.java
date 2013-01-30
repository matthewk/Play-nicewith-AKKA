package akka;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class AkkaSystem {
        public ActorSystem getActorSystem() {
            Config config = ConfigFactory.load();
            return ActorSystem.create("TestSystem", config);
        }
}
