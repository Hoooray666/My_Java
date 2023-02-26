import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        String cipher = sc.nextLine ();
        String ciphertext = cipher.toLowerCase ();
        int KeyLength = keyLength(ciphertext);

        String key = findKey (ciphertext, KeyLength);
        System.out.println ("the key is :"+key);
        String plaintext = decryption (ciphertext,key);
        System.out.println (plaintext);

    }

    private static int keyLength(String ciphertext){
        int length = 1;
        double Ic = 0;
        ArrayList<String> ciphers;
        while(true){
            ciphers = new ArrayList<> ();
            for(int i = 0 ;i< length ; i++){
                StringBuffer str = new StringBuffer ();
                for(int j = 0;i+j*length < ciphertext.length ();j++){
                    str.append (ciphertext.charAt (i+j*length));
                }
                ciphers.add (str.toString ());
            }
            Ic = IcCalculation (ciphers);
            if (Ic >= 0.06) {
                break;
            } else {
                length++;
            }
        }
        System.out.println ("length of the key is:" + length);
        System.out.println ("Ic is :"+ Ic);
        return length;
    }

    private static Double IcCalculation(ArrayList<String> list){
        double Ic = 0;
        for(String str : list){
            HashMap<Character,Integer> map = new HashMap<> ();
            for(int i = 0 ;i< 26;i++){// init the hashmap
                map.put ( (char) ( i + 97 ) , 0 );
            }
            for(int i = 0 ; i < str.length (); i++ ){
                map.put(str.charAt (i),map.get (str.charAt (i))+1);
            }
            double n = str.length ()*str.length ();
            for(int i  = 0 ; i< 26 ; i++){
                double a;
                a = map.get ((char)(i+97));
                Ic += a*(a-1)/n;
            }
        }
        Ic /= list.size ();
        return Ic;
    }

    private static String findKey(String ciphertext , int Length){

        double[] probability = new double[]{0.082, 0.015, 0.028, 0.043, 0.127, 0.022, 0.02, 0.061, 0.07, 0.002, 0.008,
                0.04, 0.024, 0.067, 0.075, 0.019, 0.001, 0.06, 0.063, 0.091, 0.028, 0.01, 0.023, 0.001, 0.02, 0.001};

        StringBuffer stringBuffer = new StringBuffer ();
        char[]key = new char[Length];
        ArrayList<String> ciphers = new ArrayList<> ();
        for(int i = 0 ;i< Length ; i++){
            StringBuffer str = new StringBuffer ();
            for(int j = 0;i+j*Length < ciphertext.length ();j++){
                str.append (ciphertext.charAt (i+j*Length));
            }
            ciphers.add (str.toString ());
        }

        for(String str : ciphers){
            int x = 0;
            double Ic[] = new double[26];
            HashMap<Character,Integer> map = new HashMap<> ();
            for(int i = 0 ;i< 26;i++){// init the hashmap
                map.put ( (char) ( i + 97 ) , 0 );
            }
            for(int i = 0 ; i < str.length (); i++ ){
                map.put(str.charAt (i),map.get (str.charAt (i))+1);
            }
            double max = 0;
            for( int j = 0; j< 26; j++){ // calculating Ic[j]
                for(int k = 0; k < 26 ;k++){// calculating Ic[k]
                    double p = probability[k];
                    double f = (double)map.get ((char)((k+j+26)%26+97))/str.length ();
                    Ic[j] += p*f;
                }
                if(max < Ic[j]){
                    max = Ic[j];
                    x = j;
                }

            }
            stringBuffer.append ((char)(x+97));
        }
        return stringBuffer.toString ();
    }

    private static String decryption(@NotNull String ciphertext, String key){
        char[] keys = key.toCharArray ();
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); ++i) {
            int j = i % key.length ();
            int change = (int) ciphertext.charAt(i) - keys[j];
            char Letter = (char) ((change < 0 ? (change + 26) : change) + 97);
            plaintext.append(Letter);
        }
        return plaintext.toString ();
    }
}
