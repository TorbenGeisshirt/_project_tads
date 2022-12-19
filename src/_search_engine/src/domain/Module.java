
package domain;

import core.KeyValuePair;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Module 
{
    public Module(String title, String content)
        throws 
            NullPointerException
    {
        if (title == null || title.isBlank())
            throw new NullPointerException("Parameter 'title' is null or blank.");

        if (content == null || content.isBlank())
            throw new NullPointerException("Parameter 'content' is null or blank."); 

        this.title = title;
        this.content = content;
    }
    
    //#region Members
    
    private String title = null;
    private String index = null;
    private String content = null;
    private Integer termCount = 0;

    private final Map<String, Double>  termTfIdf = new HashMap<>();  
    private final Map<String, Integer> termFrequency = new HashMap<>();
    private final Map<String, Integer> biGramFrequency = new HashMap<>();
    private final Map<String, Integer> triGramFrequency = new HashMap<>();
    private final Map<String, Integer> quadGramFrequency = new HashMap<>();

    //#endregion

    //#region Getters

    public String getTitle()
    {
        return title;
    }

    public String getIndex()
    {
        return index;
    }

    public String getContent()
    {
        return content;
    }

    public Integer getTermCount()
    {
        return termCount;
    }
    
    public Double getTermTfIdf(String term)
    {
        return termTfIdf.get(term);
    } 

    //#endregion

    //#region Setters

    public void setIndex(String index)
    {
        this.index = index;
    }

    public void setTermTfIdf(String term, double tfIdf)
    {
        termTfIdf.put(term, tfIdf);
    }

    //#endregion

    //#region Public Methods

    public void setTerm(String term)
    {
        this.termCount++;

        Integer frequency = null;

        if (termFrequency.containsKey(term))
        {
            frequency = termFrequency.get(term);
            frequency++;
        }

        termFrequency.put(term, frequency == null ? 1 : frequency);
    }

    public Set<String> getTerms()
    {
        return termFrequency.keySet();
    }

    public Integer getTermFrequency(String term)
    {
        return termFrequency.containsKey(term) 
                ? termFrequency.get(term) 
                : null;
    }

    public List<KeyValuePair> getMostRelevant()
    {
        PriorityQueue<KeyValuePair> queue = new PriorityQueue<>();

        for (Map.Entry<String, Double> entry : termTfIdf.entrySet()) 
        {
            KeyValuePair pair = new KeyValuePair(entry.getKey(), entry.getValue());
            queue.add(pair);
        }

        List<KeyValuePair> topTen = new ArrayList<>();

        for (int i = 0; i < 10 && !queue.isEmpty(); i++) 
            topTen.add(queue.poll());

        return topTen;
    }

    public void setBiGram(String biGram)
    {
        Integer frequency = null;

        if (biGramFrequency.containsKey(biGram))
        {
            frequency = biGramFrequency.get(biGram);
            frequency++;
        }

        biGramFrequency.put(biGram, frequency == null ? 1 : frequency);
    }

    public Integer getBiGramFrequency(String biGram)
    {
        return biGramFrequency.containsKey(biGram) 
                ? biGramFrequency.get(biGram) 
                : null;
    }

    public void setTriGram(String triGram)
    {
        Integer frequency = null;

        if (triGramFrequency.containsKey(triGram))
        {
            frequency = triGramFrequency.get(triGram);
            frequency++;
        }

        triGramFrequency.put(triGram, frequency == null ? 1 : frequency);
    }

    public Integer getTriGramFrequency(String triGram)
    {
        return triGramFrequency.containsKey(triGram) 
                ? triGramFrequency.get(triGram) 
                : null;
    }

    public void setQuadGram(String quadGram)
    {
        Integer frequency = null;

        if (quadGramFrequency.containsKey(quadGram))
        {
            frequency = quadGramFrequency.get(quadGram);
            frequency++;
        }

        quadGramFrequency.put(quadGram, frequency == null ? 1 : frequency);
    }

    public Integer getQuadGramFrequency(String quadGram)
    {
        return quadGramFrequency.containsKey(quadGram) 
                ? quadGramFrequency.get(quadGram) 
                : null;
    }

    //#endregion
}
