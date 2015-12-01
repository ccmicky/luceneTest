package com.service; /**
 * Created by ccmicky on 15-11-19.
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class distance {
    //static private long max_w = 50;
    static private long max_size = 2000;
    static private long N = 40;

    public resultData getnearnestWords(String[] args,int intger,readData rd) throws Exception {
        N = intger;
        long words = rd.words,size=rd.size;
        int a,b =0,cn=0,d,c;
        float len,dist;
        String bestw[]= new String[(int)N];
        float bestd[] = new float[(int)N];


        String vocab[] = rd.str;
        float M[][] = rd.f;

        float vec[] = new float[(int)max_size];
        String st[] = new String[100];
        long bi[] = new long[100];

        for (a = 0; a < N; a++) bestd[a] = 0;
        for (a = 0; a < N; a++) bestw[a] = "";
        System.out.println("Enter word or sentence (EXIT to break): ");
        while (cn < args.length) {
            if (args[cn].equals("EXIT")) return null;
            st[cn] = args[cn];
            cn++;
        }

        //System.out.printf("%s\n",String.valueOf(st[0]));
        for (a = 0; a < cn; a++) {
            //System.out.printf("%d\n",a);
            for (b = 0; b < words; b++) {
                if(vocab[b].trim().equals(st[a])) {
                    System.out.println("Yes");
                    break;
                }
            }
            if (b == words) b = -1;
            bi[a] = b;
            // System.out.printf("aaaa:%d\n",a);
            System.out.printf("%d\n",a);
            System.out.printf("\nWord: %s  Position in vocabulary: %d\n", st[a], bi[a]);
            if (b == -1) {
                System.out.println("Out of dictionary word!\n");
                return null;
            }
        }

        if (b == -1) return null;
        System.out.println("\nWord Cosine distance\n------------------------------------------------------------------------\n");
        for (a = 0; a < size; a++) vec[a] = 0;
        for (b = 0; b < cn; b++) {
            if (bi[b] == -1) continue;
            for (a = 0; a < size; a++) vec[a] += M[(int)bi[b]][a];
        }
        len = 0;
        for (a = 0; a < size; a++) len += vec[a] * vec[a];
        len = (float)Math.sqrt(len);
        for (a = 0; a < size; a++) vec[a] /= len;
        for (a = 0; a < N; a++) bestd[a] = -1;
        for (a = 0; a < N; a++) bestw[a] = "";
        for (c = 0; c < words; c++) {
            a = 0;
            for (b = 0; b < cn; b++) if (bi[b] == c) a = 1;
            if (a == 1) continue;
            dist = 0;
            for (a = 0; a < size; a++) dist += vec[a] * M[c][a];
            for (a = 0; a < N; a++) {
                if (dist > bestd[a]) {
                    for (d = (int)(N - 1); d > a; d--) {
                        bestd[d] = bestd[d - 1];
                        bestw[d]= bestw[d - 1];
                    }
                    bestd[a] = dist;
                    bestw[a] = vocab[c];
                    break;
                }
            }
        }
        for (a = 0; a < N; a++){ System.out.printf("%50s\t\t%f\n", bestw[a], bestd[a]);}

        resultData res = new resultData();
        res.str=bestw;
        res.f=bestd;
        return (res);

    }
}
