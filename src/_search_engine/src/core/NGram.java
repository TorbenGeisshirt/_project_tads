
package core;

import java.util.List;
import java.util.ArrayList;

public class NGram 
{
    //#region Constants

    static final int NBiGram = 2;
    static final int NTriGram = 3;
    static final int NQuadGram = 4;

    //#endregion

    //#region Static Methods

    public static List<String> getBiGrams(String content) {
        return getNGrams(content, NBiGram);
    }

    public static List<String> getTriGrams(String content) {
        return getNGrams(content, NTriGram);
    }

    public static List<String> getQuadGrams(String content) {
        return getNGrams(content, NQuadGram);
    }
    
    public static List<String> getNGrams(String content, int n)
    {
        List<String> ngrams = new ArrayList<>();

        String[] words = content.split("\\s+");

        for (int i = 0; i < (words.length - n + 1); i++) 
        {
            StringBuilder builder = new StringBuilder();

            for (int j = i; j < i + n; j++) 
                builder.append((j > i ? " " : "") + words[j]);    

            ngrams.add(builder.toString());
        }

        return ngrams;
    }

    //#endregion
}
