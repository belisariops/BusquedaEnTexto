package algorithms;

import com.sun.org.apache.xpath.internal.operations.String;

import java.io.Reader;
import java.io.Writer;

/**
 * Created by americo on 04-06-17.
 */

//  Interfaz base ára algoritmos de Patter matching

public interface IPatternMatchingAlgorithm {

    public void buildAlgorithm(Reader text);

    public void searchWord(String word, Writer result);


}
