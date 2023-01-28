
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
    private Integer biGramCount = 0;
    private Integer triGramCount = 0;

    private final Map<String, Double>  termTfIdf = new HashMap<>();
    private final Map<String, Double>  biGramTfIdf = new HashMap<>();
    private final Map<String, Double>  triGramTfIdf = new HashMap<>();

    private final Map<String, Integer> termFrequency = new HashMap<>();
    private final Map<String, Integer> biGramFrequency = new HashMap<>();
    private final Map<String, Integer> triGramFrequency = new HashMap<>();

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
    
    public Integer getBiGramCount()
    {
        return biGramCount;
    }
    
    public Integer getTriGramCount()
    {
        return triGramCount;
    }
    
    public Double getTermTfIdf(String term)
    {
        return termTfIdf.get(term);
    } 

    public Double getBiGramTfIdf(String term)
    {
        return biGramTfIdf.get(term);
    } 

    public Double getTriGramTfIdf(String term)
    {
        return triGramTfIdf.get(term);
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

    public void setBiGramTfIdf(String biGram, double tfIdf)
    {
        biGramTfIdf.put(biGram, tfIdf);
    }

    public void setTriGramTfIdf(String triGram, double tfIdf)
    {
        triGramTfIdf.put(triGram, tfIdf);
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

    public List<KeyValuePair> getTopTerms()
    {
        PriorityQueue<KeyValuePair> queue = new PriorityQueue<>();

        for (Map.Entry<String, Double> entry : termTfIdf.entrySet()) 
        {
            KeyValuePair pair = new KeyValuePair(entry.getKey(), entry.getValue());
            queue.add(pair);
        }

        List<KeyValuePair> topThree = new ArrayList<>();

        for (int i = 0; i < 3 && !queue.isEmpty(); i++) 
            topThree.add(queue.poll());

        return topThree;
    }

    public List<KeyValuePair> getTopBiGrams()
    {
        PriorityQueue<KeyValuePair> queue = new PriorityQueue<>();

        for (Map.Entry<String, Double> entry : biGramTfIdf.entrySet()) 
        {
            KeyValuePair pair = new KeyValuePair(entry.getKey(), entry.getValue());
            queue.add(pair);
        }

        List<KeyValuePair> topTwo = new ArrayList<>();

        for (int i = 0; i < 2 && !queue.isEmpty(); i++) 
            topTwo.add(queue.poll());

        return topTwo;
    }

    public List<KeyValuePair> getTopTriGrams()
    {
        PriorityQueue<KeyValuePair> queue = new PriorityQueue<>();

        for (Map.Entry<String, Double> entry : triGramTfIdf.entrySet()) 
        {
            KeyValuePair pair = new KeyValuePair(entry.getKey(), entry.getValue());
            queue.add(pair);
        }

        List<KeyValuePair> topTriGram = new ArrayList<>();

        for (int i = 0; i < 1 && !queue.isEmpty(); i++) 
            topTriGram.add(queue.poll());

        return topTriGram;
    }

    public Set<String> getBiGrams()
    {
        return biGramFrequency.keySet();
    }

    public void setBiGram(String biGram)
    {
        this.biGramCount++;

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

    public Set<String> getTriGrams()
    {
        return triGramFrequency.keySet();
    }

    public void setTriGram(String triGram)
    {
        this.triGramCount++;

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

    //#endregion
}
