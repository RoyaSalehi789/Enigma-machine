import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static int rotor1Counter = 0;
    static int rotor2Counter = 0;
    static int rotor3Counter = 0;

    public static void main(String[] args) throws IOException {
        HashMap<Character, Character> hashMap = new HashMap<>();
        HashMap<Object, Object> rotor1Map = new HashMap<>();
        HashMap<Object, Object> rotor2Map = new HashMap<>();
        HashMap<Object, Object> rotor3Map = new HashMap<>();

        Scanner inp = new Scanner(System.in);
        FileInputStream fstream = new FileInputStream("C:\\Users\\user\\Downloads\\EnigmaFile.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        System.out.println("enter the date :");
        String input = inp.nextLine();
        String inputDate = "";
        inputDate += "Date: " + input;

        StringBuilder rotor3 = new StringBuilder("");
        StringBuilder rotor2 = new StringBuilder("");
        StringBuilder rotor1 = new StringBuilder("");

        for (int j = 0; j < 500; j+=5) {
                strLine = br.readLine();
                if (strLine.equals(inputDate)) {
                    //plugBoard
                    strLine = br.readLine();
                    boolean startRead = false;
                    StringBuilder plugBoard = new StringBuilder("");
                    for (int i = 0; i < strLine.length(); i++) {

                        if ('[' == strLine.charAt(i)) {
                            startRead = true;
                        } else if (']' == strLine.charAt(i)) {
                            startRead = false;
                        } else if (startRead) {
                            plugBoard.append(strLine.charAt(i));
                        }
                    }
                    for (int i = 0; i < plugBoard.length() - 1; i++) {
                        if ((plugBoard.charAt(i) >= 97 && plugBoard.charAt(i) <= 122) && (plugBoard.charAt(i + 1) >=
                                97 && plugBoard.charAt(i + 1) <= 122)) {
                            setPlugBoard(plugBoard.charAt(i), plugBoard.charAt(i + 1), hashMap);
                        }
                    }
                    //rotor1
                    strLine = br.readLine();
                    startRead = false;

                    for (int i = 0; i < strLine.length(); i++) {

                        if ('[' == strLine.charAt(i)) {
                            startRead = true;
                        } else if (']' == strLine.charAt(i)) {
                            startRead = false;
                        } else if (startRead) {
                            rotor1.append(strLine.charAt(i));
                        }
                    }
                    setRotor(rotor1, rotor1Map);

                    //rotor2
                    strLine = br.readLine();
                    startRead = false;

                    for (int i = 0; i < strLine.length(); i++) {

                        if ('[' == strLine.charAt(i)) {
                            startRead = true;
                        } else if (']' == strLine.charAt(i)) {
                            startRead = false;
                        } else if (startRead) {
                            rotor2.append(strLine.charAt(i));
                        }
                    }
                    setRotor(rotor2, rotor2Map);


                    //rotor3
                    strLine = br.readLine();
                    startRead = false;

                    for (int i = 0; i < strLine.length(); i++) {

                        if ('[' == strLine.charAt(i)) {
                            startRead = true;
                        } else if (']' == strLine.charAt(i)) {
                            startRead = false;
                        } else if (startRead) {
                            rotor3.append(strLine.charAt(i));
                        }
                    }
                    setRotor(rotor3, rotor3Map);

                    fstream.close();
                }
        }
        System.out.println("enter the input:");
        String inputString = inp.nextLine();
        String result = "";
        //date
        for (int i = 0; i < inputString.length(); i++) {
            char character;
            character = inputString.charAt(i);

            character = plugBoard(hashMap, character);

            character = findInRotor(character, rotor3Map);
            character = findInRotor(character, rotor2Map);
            character = findInRotor(character, rotor1Map);

            character = getReflectorResponse(character);

            character = reverseMap(rotor1Map).get(character);
            character = reverseMap(rotor2Map).get(character);
            character = reverseMap(rotor3Map).get(character);

            result += plugBoard(hashMap, character);
            if(rotor3Counter<26){
                rotor3= new StringBuilder(turnRotor(String.valueOf(rotor3)));
                setRotor(rotor3,rotor3Map);
                rotor3Counter++;
            }
            else{
                if(rotor2Counter<26){
                    rotor2= new StringBuilder(turnRotor(String.valueOf(rotor2)));
                    setRotor(rotor2,rotor2Map);
                    rotor2Counter++;
                }
                else{
                    if(rotor1Counter<26){
                        rotor1= new StringBuilder(turnRotor(String.valueOf(rotor1)));
                        setRotor(rotor1,rotor1Map);
                        rotor1Counter++;
                    }
                }
            }
        }
        System.out.println(result);
    }

    static char getReflectorResponse(char character) {
        int distance = (int) character - 97;
        int mappedItem = 122 - distance;
        return (char) mappedItem;
    }

    static void setPlugBoard(char c1, char c2, HashMap<Character, Character> inputMapped) {
        inputMapped.put(c1, c2);
    }

    public static <K, V> K getKey(Map<K, V> inputItems, V value) {
        for (Map.Entry<K, V> entry : inputItems.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static <K, V> V getValue(Map<K, V> inputItems, K key) {
        for (Map.Entry<K, V> entry : inputItems.entrySet()) {
            if (key.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static char plugBoard(Map<Character, Character> inputItems, char item) {
        if (getKey(inputItems, item) == null) {
            if (getValue(inputItems, item) == null) {
                return item;
            } else {
                return getValue(inputItems, item);
            }

        } else {
            return getKey(inputItems, item);
        }
    }

    public static <K> char findInRotor(K key, Map rotor) {
        return (char) getValue(rotor, key);
    }

    public static void setRotor(StringBuilder str, HashMap hashMap) {
        for (int i = 0; i < str.length(); i++) {
            hashMap.put(((char) (i + 97)), str.charAt(i));
        }

    }

    public static Map<Character, Character> reverseMap(Map map) {
        Map reversedMap = new HashMap();
        for (int i = 0; i < map.size(); i++) {
            reversedMap.put(map.get((char) (97 + i)), (char) (i + 97));
        }
        return reversedMap;
    }

    public static String turnRotor(String rotor){
        String str="";
        str+=rotor.charAt(rotor.length()-1);
        str+=rotor.substring(0,rotor.length()-1);
        return str;
    }
}

