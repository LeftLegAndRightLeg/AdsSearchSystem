package com.main.queryParser;

/**
 * Created by gongbailiang on 9/1/16.
 */
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.AttributeFactory;
import org.springframework.stereotype.Service;

@Service
public class QueryParser {

    public List<String> parseQuery(String queryStr) {
        List<String> tokens = new ArrayList<String>();
        AttributeFactory factory = AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY;
        Tokenizer tokenizer = new StandardTokenizer(factory);
        tokenizer.setReader(new StringReader(queryStr));
        CharArraySet stopWords = EnglishAnalyzer.getDefaultStopSet();
        TokenStream tokenStream = new StopFilter(tokenizer, stopWords);
        CharTermAttribute charTermAttribute = tokenizer.addAttribute(CharTermAttribute.class);
        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                String term = charTermAttribute.toString();

                tokens.add(term);
            }
            tokenStream.end();
            tokenStream.close();

            tokenizer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tokens;
    }
}