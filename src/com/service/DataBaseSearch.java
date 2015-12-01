package com.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.jms.Message;
import javax.swing.text.Highlighter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 * Created by ccmicky on 15-9-2.
 */
public class DataBaseSearch {
    //public void search(String keyword,int first, int max,Sort sort) throws Exception {
    public void search(String keyword, String[] str) throws Exception {
        if (str.length > 0)
        {
            getDate gd = new getDate();
            String strtime = gd.GetNowDate();
            String searchStr = keyword;
            searchStr = searchStr.replace(" ", "");
            String filePath="/home/hadoop/search/index";
            FileWriter fileWriter = new FileWriter("/home/hadoop/search/result/" + searchStr + "-" + strtime + ".txt");
            if (searchStr.equals("")) {
                fileWriter = new FileWriter("/home/hadoop/search/result/" + "Total-" + strtime + ".txt");
            }

            //FileWriter fileWriterTotal=new FileWriter("/home/hadoop/search/result/Total"+searchStr+"-"+strtime+".txt");
            //FileWriter fileWriterContent=new FileWriter("/home/hadoop/search/result/Content"+searchStr+"-"+strtime+".txt");
            Directory dir= FSDirectory.open(new File(filePath));
            IndexReader reader= DirectoryReader.open(dir);
            IndexSearcher searcher=new IndexSearcher(reader);
            Analyzer analyzer = new IKAnalyzer(true);
            TokenStream stream = analyzer.tokenStream("", new StringReader(searchStr));
            TopDocs topdocs = null;
            if (searchStr.equals(""))
            {
                Term term=new Term("Tag", searchStr);
                TermQuery query=new TermQuery(term);
                topdocs=searcher.search(query,19999999);
            }
            else {
                BooleanQuery query = new BooleanQuery();

                //TokenStream stream = null;

                stream.reset();
                while (stream.incrementToken()) {
                    for(int i =0;i<str.length;i++)
                    {
                        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                        System.out.println(str[i]);
                        TermQuery termQuery = new TermQuery(new Term(str[i], stream.getAttribute(CharTermAttribute.class).toString()));
                        query.add(termQuery, BooleanClause.Occur.SHOULD);
                    }
                    //TermQuery termQuery = new TermQuery(new Term("Review_Content", stream.getAttribute(CharTermAttribute.class).toString()));
                    //TermQuery termQueryhid = new TermQuery(new Term("Review_id", stream.getAttribute(CharTermAttribute.class).toString()));
                    //System.out.println(termQuery);
                    //query.add(termQuery, BooleanClause.Occur.SHOULD);
                    //query.add(termQueryhid,BooleanClause.Occur.SHOULD);
                }


                stream.close();
                //TopFieldCollector c = TopFieldCollector.create(sort, first + max, false, false, false, false);
                //searcher.search(query, c);
                //ScoreDoc[] scoreDocs=c.topDocs(first, max).scoreDocs;
                //System.out.println(scoreDocs.length);
                topdocs = searcher.search(query, 19999999);
            }
            ScoreDoc[] scoreDocs=topdocs.scoreDocs;
            if (scoreDocs == null || scoreDocs.length < 1)
                return ;

            List<String> sentlist = new ArrayList<String>();
            //System.out.println("查询结果总数---" + c.getTotalHits()+"最大的评分--");
            System.out.println("查询结果总数---" + topdocs.totalHits + "最大的评分--" + topdocs.getMaxScore());
            for(int i=0; i < scoreDocs.length; i++) {
                int doc = scoreDocs[i].doc;
                String sent = null;
                Document document = searcher.doc(doc);

                //System.out.println("Hotel_id===="+document.get("Review_id"));
                //System.out.println("Review_Content===="+document.get("Review_Content"));
                //System.out.println("id--" + scoreDocs[i].doc + "---scors--" + scoreDocs[i].score+"---index--"+scoreDocs[i].shardIndex);
                stream = analyzer.tokenStream("", new StringReader(document.get("Review_Content")));
                stream.reset();
                String outstr = "";
                while (stream.incrementToken()) {
                    outstr += stream.getAttribute(CharTermAttribute.class).toString() + " ";
                }
                stream.close();

                String s = "";
                //fileWriter.write("Review_id:"+document.get("Review_id")+"|^|"+"Hotel_id:"+document.get("Hotel_id")+"|^|"+"Review_Content:"+out+"\n");
                sent = document.get("Review_id") + "\t" + document.get("Hotel_id") + "\t" + outstr + '\n';
                fileWriter.write(document.get("Review_id") + "\t" + document.get("Hotel_id") + "\t" + outstr + "\t" + document.get("id") + "\n");
            }
            fileWriter.close();
            fileWriter.flush();
            //fileWriter.flush();
            //ridlist.add(document.get("Review_id"));
            /*Term term=new Term("Review_id", document.get("Review_id"));
            TermQuery termquery=new TermQuery(term);
            TopDocs senttopdocs=searcher.search(termquery,1000);
            ScoreDoc[] sentscoreDocs = senttopdocs.scoreDocs;

            for (int j=0; j < sentscoreDocs.length; j++)
            {
                int sentdoc = sentscoreDocs[j].doc;
                Document sentdocument = searcher.doc(sentdoc);


                if (j == sentscoreDocs.length-1)
                {
                    sent += sentdocument.get("Review_Content").trim()+"。\n";
                    s+= sentdocument.get("Review_Content").trim()+"。\n";
                }
                else
                {
                    sent += sentdocument.get("Review_Content").trim()+",";
                    s+= sentdocument.get("Review_Content").trim()+",";
                }
            }
            fileWriterTotal.write(sent);
            //fileWriterTotal.flush();
            sentlist.add(sent);
            stream = analyzer.tokenStream("", new StringReader(s));
            stream.reset();
            String outs = "";
            while(stream.incrementToken()){
                outs +=  stream.getAttribute(CharTermAttribute.class).toString() + " ";
            }
            stream.close();
            fileWriterContent.write(outs + '\n');
            //fileWriterContent.flush();
        }
        fileWriter.flush();
        fileWriterTotal.flush();
        fileWriterContent.flush();

        fileWriterTotal.close();
        fileWriterContent.close();
        fileWriter.close();
        reader.close();*/
            // return new SearchResultBean(c.getTotalHits(), sentlist);
        }


    }

}
