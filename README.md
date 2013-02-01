Play-nicewith-AKKA
==================

AKKA implementation for Play 1.2.x
# Update!
I have removed the need to call play with the config.file system property. Instead I am using:
    `ConfigFactory.parseFile(Play.getFile("conf/reference.conf"));`
# Overview
This project provides a very basic skeleton for integrating AKKA with Play 1.2.x. Everything is included in the project - to run do the following:
- in a command prompt run `play deps` - this will get the AKKA client jar
- call 
	`play run`

The database is in memory and so will be cleared every time you run the project.

# Setup Guide (If creating a project manually)

- In your dependencies.yml file include the line

  `com.typesafe.akka -> akka-actor_2.10 2.1.0 // this was the latest version at time of writing`

- execute from the command line **play deps** to download the relevant libraries into your lib folder
- do some IDE goodness to get the library dependencies in
setup in your conf directory a reference.conf file (this will contain config info for akka on start) - look at: http://doc.akka.io/docs/akka/2.1.0/general/configuration.html
- add `"-Dconfig.file=reference.conf"` to your VM options
- Have a static class of AppContext or similar to load in the AkkaSystem - this is just so that you create once and can reference from anywhere.
- Load the AKKA system with:

	    Config config = ConfigFactory.load();
	    public static final notificationSystem = ActorSystem.create("notification", config);

That should be enough to get running, then you just have to create some Actors to do your bidding.

I also got JPA working - if you try and access your models and lookup data, you will end up with an Uninitialised Entity Manager error.

Here is some example code for an actor with JPA access:

	    @Override
	    public void onReceive(Object o) throws Exception {
		    if (o instanceof SomeMessageType) { // create your own serializable class to transfer message data
		        SomeMessageType message = (SomeMessageType) o;
		        JPAPlugin.startTx(true); // true is to start this as a readonly transaction
		        // Do all the JPA goodness
		    
		        JPAPlugin.closeTx(false); // false is for rollback - I use this because I am doing a readonly transaction
		    
		    } else {
		        unhandled(o);
		    }
	    }

Hope this helps. I spent a good few hours trying to get everything working. I am now able to structure my async tasks rather than just spit out to Play Jobs.
