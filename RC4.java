import java.util.Arrays;
/**
 * @Author: Francis
 * @Date: 2023-03-30-16:33
 * @Description: written by xiaoshizi
 */
public class RC4 {
    static int[] S = {0,1,2,3};
    public static void main(String[] args) {
        int[] T = {1,2,3,1};
        int j = 0;
        for(int i =0 ;i < 4;i++){
            j = (j+S[i]+T[i])%4;
            System.out.println("j="+j);
            int tmp = 0;
            tmp = S[i];
            S[i] = S[j];
            S[j] = tmp;
            System.out.println(Arrays.toString(S));
        }
//        System.out.println(Arrays.toString(S));
        System.out.println("-----S盒构造完成-----");
        j = 0;
        int i =0;
        int cnt =0;
        StringBuffer s = new StringBuffer();
        while(cnt<7){
            i=(i+1)%4;
            j = (j+S[i])%4;
            System.out.println("i="+i+" j="+j);
            int tmp = 0;
            tmp = S[i];
            S[i] = S[j];
            S[j] = tmp;
            System.out.println(Arrays.toString(S));
            tmp = (S[i]+S[j])%4;
            System.out.println(S[tmp]);
            s.append(S[tmp]);
//            System.out.println(s);
            cnt++;
        }
        System.out.println(s);
    }
}
