package models;

import java.util.Random;

/**
 * Created by hayden on 3/24/17.
 */
public class Helper {

    /**
     * Set of static functions needed throughout the codebase
     */

    /**
     * Return a random number between given intervals
     * @param start
     * @param end
     * @return a random number
     */
    public static int randomNumberBetweenInterval(int start, int end) {
        Random r = new Random();

        if (start == 1 && end == 1) {
            return 1;
        } else {
            return r.nextInt((end-start) + 1) + start;
        }

    }
}
