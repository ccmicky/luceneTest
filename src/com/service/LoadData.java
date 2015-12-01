package com.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by ccmicky on 15-11-20.
 */
public class LoadData {
    public readData initDatafromFile(){
        String file_name = "/home/ccmicky/word2vec/trunk/idvectors.bin";
        long  words=0,size=0;
        int a,b =0;
        float len;
        try {

            //ArrayList<Character> vocab = new ArrayList<Character>();
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file_name));//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String str = bufferedReader.readLine();
            String strlist[] = str.split(" ");
            if (strlist.length == 2) {
                words = (long) Integer.parseInt(strlist[0]);
                size = (long) Integer.parseInt(strlist[1]);
                //System.out.println(words);
            }
            String vocab[] = new String[(int) (words)];
            float M[][] = new float[(int) (words)][(int) (size) + 1];

            String strline = null;

            while ((strline = bufferedReader.readLine()) != null && b < words) {
                String[] strarray = new String[(int)size+1];
                strarray = strline.trim().split(" ");
                //System.out.println(strline);
                if (strarray.length == size + 1) {
                    vocab[b] = strarray[0];
                    //System.out.println(vocab[b]);
                    for (a = 1; a < size + 1; a++) {
                        M[b][a] = Float.valueOf(strarray[a]);
                    }
                }
                len = 0;
                for (a = 0; a < size; a++)
                    len += M[b][a] * M[b][a];
                len = (float) Math.sqrt(len);
                for (a = 0; a < size; a++)
                    M[b][a] /= len;
                b++;
            }
            bufferedReader.close();
            read.close();
            readData rd = new readData();
            rd.str = vocab;
            rd.f =M;
            rd.words=words;
            rd.size=size;
            return (rd);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
