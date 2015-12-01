package com.service;

import java.util.List;
import org.apache.lucene.document.Document;
public class SearchResultBean
{
    public int totalHits;
    public List<String> sentlist;
    public SearchResultBean()
    {
    }
    public SearchResultBean(int totalHits, List<String> docs)
    {

        this.totalHits = totalHits;
        this.sentlist = docs;
    }
    public int getTotalHits()
    {

        return this.totalHits;
    }
    public void setTotalHits(int totalHits)
    {
        this.totalHits = totalHits;
    }
    public List<String> getsentlist()
    {
        return this.sentlist;
    }
    public void setsentlist(List<String> docs)
    {
        this.sentlist = docs;
    }
}
