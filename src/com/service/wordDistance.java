package com.service;

/**
 * Created by ccmicky on 15-11-19.
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class wordDistance {
    static private long max_w = 50;
    static private long max_size = 2000;
    static private long N = 40;
    public void getnearnestWords(String[] args) throws Exception {
        args[0]="酒店";
        String file_name = "/home/ccmicky/gcc/Totalvectors.bin";
        Character c;
        int cc;
        long  words=0,size = 0;
        int a,b =0,cn=0,d;
        float len=0,dist=0;

        try{
            //ArrayList<Character> vocab = new ArrayList<Character>();
            String strlist[] = new String[100];
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file_name));//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String str = bufferedReader.readLine();
            strlist = str.split(" ");
            if (strlist.length==2){
                words = (long)Integer.parseInt(strlist[0]);
                size = (long)Integer.parseInt(strlist[1]);
            }

            String vocab[] = new String[(int)(words)];
            String bestw[]= new String[(int)N];
            float bestd[] = new float[(int)N];
            float vec[] = new float[(int)max_size];
            char st1[] = new char[(int)max_size];
            String st[] = new String[100];
            float M[][] = new float[(int)(words)][(int)(size)+1];
            long bi[] = new long[100];
            String strline = null;
            while((strline=bufferedReader.readLine()) !=null && b <words)
            {
                String[] strarray = new String[(int)size+1];
                strarray = strline.trim().split(" ");
                if(strarray.length==size+1)
                {
                    vocab[b] = strline.split(" ")[0];
                    //System.out.println(vocab[b]);
                    for (a=1;a<size+1;a++)
                    {
                        M[b][a]=Float.valueOf(strline.split(" ")[a]);
                    }
                }
                len = 0;
                for (a = 0; a < size; a++)
                    len += M[b][a] * M[b][a];
                len = (float)Math.sqrt(len);
                for (a = 0; a < size; a++)
                    M[b][a] /= len;
                b++;
            }
            bufferedReader.close();
            read.close();
            for (a = 0; a < N; a++) bestd[a] = 0;
            for (a = 0; a < N; a++) bestw[a] = "";
            System.out.println("Enter word or sentence (EXIT to break): ");
            a = 0;
            while (cn < args.length) {
                if (args[cn].equals("EXIT")) return;
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
                    return;
                }
            }

            if (b == -1) return;
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


    	/*DataInputStream in=new DataInputStream(new BufferedInputStream(new FileInputStream(file_name)));
        if(in != null)
        {
            System.out.println(in.read());
        }
        in.close();*/
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
