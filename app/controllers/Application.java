package controllers;

import akka.dispatchers.CalculationDispatcher;
import models.Result;
import play.mvc.Controller;

import java.util.List;

public class Application extends Controller {

    public static void index() {
        List<Result> results = Result.getResults();
        render(results);
    }

    public static void clearResults(){
        Result.deleteAll();
        index();
    }

    public static void doPredefinedCalc(){
        for (int i=0; i<10; i++){
            new CalculationDispatcher(100*i, 10*i + " Iterations").doCalculation();
            new CalculationDispatcher(100*i, 10*i + " Iterations").doDelayedCalculation(10L);
        }
        index();
    }

    public static void doCalculation(int iterations, String description, Long delay){
        CalculationDispatcher dispatcher = new CalculationDispatcher(iterations, description);
        if (delay > 0){
            dispatcher.doDelayedCalculation(delay);
        } else {
            dispatcher.doCalculation();
        }
        index();
    }

}