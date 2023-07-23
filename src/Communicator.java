import java.util.ArrayList;
import java.util.List;

public class Communicator {

    private List<List<Integer>> addressesBinary;

    public Communicator(){
        this.addressesBinary = createdAddressesInBinary();
    }



    public List<Integer> createPreparedCode(List<Integer> rawCode){

        List<Integer> errors = getErrors(rawCode);

        for (int i = 0; i < errors.size(); i++){
            if (errors.get(i) == 1){
                changeParityBits( rawCode ,(int) Math.pow(2, (errors.size()-i)-1));
            }
        }
        return rawCode;
    }


    public List<Integer> createRawCode(List<Integer> randomMessageInBinary){

        List<Integer> code = new ArrayList<>();
        for (int i = 0; i < this.addressesBinary.size(); i ++){
            code.add(0);
        }

        int n = 0;
        while (Math.pow(2, n) < code.size()){
            code.set((int) Math.pow(2, n), 1);
            n++;
        }

        for (int i = 1; i < code.size(); i++){

            if (code.get(i) == 0){

                code.set(i, randomMessageInBinary.get(0));
                randomMessageInBinary.remove(0);
                if (randomMessageInBinary.size() == 0){
                    break;
                }
            }
        }
        return code;
    }



    public List<Integer> getErrors(List<Integer> rawCode){

        List<List<Integer>> onlyOnes = new ArrayList<>();

        for (int b = 0; b < rawCode.size(); b++){
            if (rawCode.get(b) == 1){
                onlyOnes.add(this.createdAddressesInBinary().get(b));
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < onlyOnes.get(0).size(); i++){
            int count = 0;
            for (int j = 0; j < onlyOnes.size(); j++){
                count = count + onlyOnes.get(j).get(i);
            }
            result.add(count % 2);
        }
        return result;
    }


    public void changeParityBits(List<Integer> code ,int index){

        if (code.get(index) == 1){
            code.set(index, 0);
        }else {
            code.set(index, 1);
        }
    }

    public List<Integer> createRandomMessageInBinary(){
        List<Integer> message = new ArrayList<>();

        for (int i = 0; i < 11; i++){

            if (Math.random() < 0.5){
                message.add(0);
            }else {
                message.add(1);
            }
        }
        return message;
    }

    public List<Integer> intToBinary(int number){

        List<Integer> binary = new ArrayList<>();

        while (number > 0){

            int reminder = number % 2;
            binary.add(0, reminder);
            number = number / 2;
        }
        return binary;
    }

    public List<List<Integer>> createdAddressesInBinary(){

        int count_biggest = 0;
        List<List<Integer>> binaryAddresses = new ArrayList<>();
        for (int i = 0; i < 16; i++){
            binaryAddresses.add(intToBinary(i));
            if (intToBinary(i).size() > count_biggest){
                count_biggest = intToBinary(i).size();
            }
        }

        for (List<Integer> binary: binaryAddresses) {
            while (binary.size() < count_biggest){
                binary.add(0, 0);
            }
        }
        return binaryAddresses;
    }


    public List<Integer> includeError(List<Integer> code) {

        int min = 0;
        int max = 15;
        int randomInt = (int) ((Math.random() * (max - min)) + min);

        if (code.get(randomInt) == 0){
            code.set(randomInt, 1);
        }else {
            code.set(randomInt, 0);
        }

        return code;
    }

    public List<Integer> recoverMessage(List<Integer> code) {

        int n = 0;
        while (Math.pow(2, n) < code.size()){
            code.set((int) Math.pow(2, n), -1);
            n++;
        }
        code.set(0,-1);

        List<Integer> recoveredMessage = new ArrayList<>();

        for (Integer temp: code) {
            if (temp != -1){
                recoveredMessage.add(temp);
            }
        }

        return recoveredMessage;
    }

    public List<Integer> resolveError(List<Integer> code, List<Integer> error){

        int indexOfError = binaryToInt(error);

        if (code.get(indexOfError) == 1){
            code.set(indexOfError, 0);
        }else {
            code.set(indexOfError, 1);
        }

        return code;
    }

    public int binaryToInt(List<Integer> error) {

        int number = 0;

        for (int i = 0; i< error.size(); i++){
            if (error.get(i) == 1){
                number = number + (int) (Math.pow(2, (error.size() - 1 - i)));
            }
        }

        return number;
    }

}
