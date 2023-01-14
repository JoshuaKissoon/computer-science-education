import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Assignment https://coursera.cs.princeton.edu/algs4/assignments/hello/specification.php
 */
public class RandomWord {

    public static void main(String[] args) {
        String champion = "";
        int i = 0;

        while (!StdIn.isEmpty()) {
            i++;
            String contender = StdIn.readString();

            /* Let's check the probability that this word is the champion */
            Boolean isChampion = StdRandom.bernoulli((1.0 / i));
            if(isChampion) {
                champion = contender;
            }
        }

        System.out.println(champion);
    }

}
