package algorithms;

import com.sun.org.apache.xpath.internal.operations.String;

/**
 * Created by americo on 04-06-17.
 */

//  Interfaz base ára algoritmos de Patter matching

public interface IPatternMatchingAlgorithm {

    public void buildAlgorithm(String text);

    public void searchWord(String word, String result);


}
