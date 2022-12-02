package seamcarving.seamfinding;

import seamcarving.Picture;
import seamcarving.SeamCarver;
import seamcarving.energy.EnergyFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 * @see SeamCarver
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    @Override
    public List<Integer> findHorizontal(Picture picture, EnergyFunction f) {
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");

        List<Integer> res = new ArrayList<>();
        double[][] dp = new double[picture.width()][picture.height()];

        for (int i = 0; i < picture.height(); ++i) {
            dp[0][i] = f.apply(picture, 0, i);
        }

        for (int x = 1; x < picture.width(); ++x) {
            for (int y = 0; y < picture.height(); ++y) {
                double preMin = Double.MAX_VALUE;
                for (int z = y - 1; z <= y + 1; ++z) {
                    if (z >= 0 && z < picture.height()) {
                        preMin = Math.min(preMin,dp[x-1][z]);
                    }
                }
                dp[x][y] = preMin + f.apply(picture, x, y);
            }
        }

        int yMin = 0;
        double valMin = Double.MAX_VALUE;

        for (int y = 0; y < picture.height(); ++y) {
            if (dp[picture.width()-1][y] <= valMin) {
                yMin = y;
                valMin = dp[picture.width()-1][y];
            }
        }
        res.add(yMin);

        for (int x = picture.width()-2; x > -1; --x) {
            valMin = Double.MAX_VALUE;
            int lower = yMin == 0 ? 0 : yMin - 1;
            int upper = yMin == picture.height() - 1 ? yMin : yMin + 1;

            for (int z = lower; z <= upper; ++z) {
                if (dp[x][z] <= valMin) {
                    yMin = z;
                    valMin = dp[x][z];
                }
            }
            res.add(yMin);
        }

        Collections.reverse(res);
        return res;
    }
}
