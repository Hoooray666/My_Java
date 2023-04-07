import java.util.Arrays;

public class DES {
    static char[] C = new char[28];
    static char[] D = new char[28];
    static int[]pc1 = {57,49,41,33,25,17,9,
            1,58,50,42,32,26,18,
            10,2,59,51,43,35,27,
            19,11,3,60,52,44,36,
            63,55,47,39,31,23,15,
            7,62,54,46,38,30,22,
            14,6,61,53,45,37,29,
            21,13,5,28,20,12,4};
    static int[]pc2 = {14,17,11,24,1,5,3,28,
            15,6,21,10,23,19,12,4,
            26,8,16,7,27,20,13,2,
            41,52,31,37,47,55,30,40,
            51,45,33,48,44,49,39,56,
            34,53,46,42,50,36,29,32};
    public static void main(String[] args) {
        String str  = "0111100001101001011000010110111101110011011010000110100101111010";
        String str2 = "1110000111100001111000011110000111110000111100001111000011110000";
        char[]arr = str.toCharArray();
        char[]arr1 = str2.toCharArray();
//        System.out.println(arr.length);
//        int[] arr1 = new int[56];
//        System.out.println(pc1(arr));
        StringBuffer [] s1 = getSubkey(str);
        int[] cnt = new int[16];
        for(int i = 0;i< 16;i++){
            String string = s1[i%16].toString();
            String string1 = s1[(i+1)%16].toString();
            for(int j = 0 ; j< 48;j++){
                if(string.charAt(j)!=(string1.charAt(j))){
                    cnt[i]++;
                }
            }
        }
        System.out.println(Arrays.toString(cnt));

    }
    public static char[] pc1(char[] arr){
        char[] arr1 = new char[56];
        for(int i = 0; i<56;i++){
            arr1[i] = arr[pc1[i]-1];
        }
//        System.out.println(arr[32]);
//        System.out.println(arr1[11]);
        for(int i = 0; i<28;i++){
            C[i] = arr1[i];
            D[i] = arr1[i+28];
        }
        System.out.println(C);
        System.out.println(D);
        return arr1;
    }
    public static StringBuffer[] getSubkey(String str) {
//        StringBuffer keyBinary = new StringBuffer(stringBufferToBinary(key)); //把密钥转成二进制
        StringBuffer keyBinary = new StringBuffer(str);
//        String w = keyBinary.toString();
//        System.out.println(w);
        StringBuffer[] subkey = new StringBuffer[16];  //subkey数组用来存储子密钥
        StringBuffer C0 = new StringBuffer(); //左密钥
        StringBuffer D0 = new StringBuffer(); //右密钥
        //判断密钥长度
        while (keyBinary.length() < 64) {
            keyBinary.append("0");
        }
        //PC1置换（64 bits --> 56 bits）
        for (int i = 0; i < 28; i++) {
            C0.append(keyBinary.charAt(pc1[i] - 1));
            D0.append(keyBinary.charAt(pc1[i + 28] - 1));
        }
        //16轮循环生成子密钥
        //16轮移位操作，每轮左移一位，特殊情况左移两位（查看密钥移位表）
        for (int i = 0; i < 16; i++) {
            //把第一位删了添加到最后一位
            char tmp;
            tmp = C0.charAt(0);
            C0.deleteCharAt(0);
            C0.append(tmp);
            tmp = D0.charAt(0);
            D0.deleteCharAt(0);
            D0.append(tmp);
            //特殊位置左移两位
            if (i != 0 && i != 1 && i != 8 && i != 15) {
                tmp = C0.charAt(0);
                C0.deleteCharAt(0);
                C0.append(tmp);
                tmp = D0.charAt(0);
                D0.deleteCharAt(0);
                D0.append(tmp);
            }
            //左右合并
            StringBuffer CODO = new StringBuffer(C0.toString() + D0.toString());
            //PC2置换
            StringBuffer C0D0tmp = new StringBuffer();
            for (int j = 0; j < 48; j++) {
                C0D0tmp.append(CODO.charAt(pc2[j] - 1));
            }
            subkey[i] = C0D0tmp;
              System.out.println(i + "轮密钥：" + subkey[i]);
        }
//        for(StringBuffer s :subkey){
//            String s1 = s.toString();
//            System.out.println(s1);
//        }
        return subkey;
    }

}
