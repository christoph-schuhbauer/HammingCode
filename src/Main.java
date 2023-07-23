import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Main {


    public static void testRun(){

        Communicator sender = new Communicator();

        List<Integer> randomMessage = sender.createRandomMessageInBinary();
        System.out.println(randomMessage + " is message");

        List<Integer> rawCode  = sender.createRawCode(randomMessage);
        List<Integer> code = sender.createPreparedCode(rawCode);
        System.out.println(code + " was send");

        Random rd = new Random();
        double randomDouble = rd.nextDouble();

        if (randomDouble < 0.5){
            code = sender.includeError(code);
        }

        Communicator receiver = new Communicator();
        List<Integer> receivedCode = code;
        System.out.println(receivedCode + " was received");


        List<Integer> checkResult = receiver.getErrors(receivedCode);
        if (checkResult.contains(1)){
            System.out.println("found error: " + checkResult);
            receivedCode = receiver.resolveError(receivedCode, checkResult);
        }


        List<Integer> recoveredMessage = receiver.recoverMessage(receivedCode);
        System.out.println(recoveredMessage + " was recovered");


    }




    public static void main(String[] args) {

        testRun();

    }
}