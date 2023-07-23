import java.lang.Package;
import java.util.ArrayList;
import java.util.List;

public class HammingCode {

    private List<Integer> message;


    public HammingCode(int message){
        this.message = intToBinary(message);
    }



    public List<Integer> intToBinary(int message){

        List<Integer> binary = new ArrayList<>();
        if (message == 0){
            binary.add(0);
        }
        while (message > 0){
            int reminder = message % 2;
            binary.add(0, reminder);
            message /= 2;
        }

        return binary;
    }


    public List<Integer> getParityPositions(List<Integer> binaryMessage){
        int n = 0;
        List<Integer> parityPositions = new ArrayList<>();
        while (Math.pow(2, n) <= binaryMessage.size()){
            parityPositions.add((int) Math.pow(2, n));
            n++;
        }
        return parityPositions;
    }

    public List<Integer> insertZerosInMessage(){

        List<Integer> messageAsBinary = this.message;
        List<Integer> parityPositions = getParityPositions(messageAsBinary);
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i<= messageAsBinary.size()+ parityPositions.size(); i++){
            result.add(5);
        }

        for (int i = 0; i < result.size(); i++){

            if (parityPositions.contains(i+1)){
                result.set(result.size()- 1 - i, -1);
            }else {
                result.set( result.size()- 1 - i, messageAsBinary.get(messageAsBinary.size() -1));
                messageAsBinary.remove(messageAsBinary.size() -1);

            }
        }
        return result;
    }

    public List<List<Integer>> getOnePositionsBinary(List<Integer> buffedMessage){

        int sizeCount = 0;
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i< buffedMessage.size();i++){
            if (buffedMessage.get(i) == 1){
                result.add(intToBinary(buffedMessage.size() - i));
                if ((intToBinary(buffedMessage.size() - i)).size() > sizeCount){
                    sizeCount = (intToBinary(buffedMessage.size() - i)).size();
                }
            }
        }

        for (List<Integer> temp:result) {
            while (temp.size() < sizeCount){
                temp.add(0,0);
            }
        }

        return result;
    }




    public List<Integer> add(List<List<Integer>> Ones){

        List<Integer> result = new ArrayList<>();

        for (int i = 0;i < Ones.get(0).size(); i++){

            int number = 0;

            for (int j = 0; j < Ones.size(); j++){
                number = number + Ones.get(j).get(i);
            }
            result.add(number % 2);

        }
        return result;
    }


    public void addRealBits(List<Integer> realBits, List<Integer> message){


        System.out.println(message);
        System.out.println(realBits);


        for (int i = 0; i < message.size(); i++){
            if (message.get(i) == -1){

                message.set(i, realBits.get(0));
                realBits.remove(0);


            }
        }



        System.out.println(message);



    }


}


