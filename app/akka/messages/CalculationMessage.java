package akka.messages;

/**
 * Created with IntelliJ IDEA.
 * User: matt
 * Date: 30/01/2013
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public class CalculationMessage {

    public CalculationMessage(int iterations, String description) {
        this.iterations = iterations;
        this.description = description;
    }

    public int iterations;
    public String description;
    public Long resultID;


}
