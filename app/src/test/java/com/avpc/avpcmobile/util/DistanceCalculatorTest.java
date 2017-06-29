package com.avpc.avpcmobile.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by thiago on 19/06/17.
 */
public class DistanceCalculatorTest {
    @Test
    public void getDistanceFromLatLonInKm() throws Exception {
        double latitude1 = 51.5287714;
        double longitude1 = -0.2420434;
        double latitude2 = 41.4103908;
        double longitude2 = 2.1941609;

        Assert.assertTrue(true);
        double distance =
                DistanceCalculator.getDistanceFromLatLonInKm(
                                            latitude1
                                            ,longitude1
                                            ,latitude2
                                            ,longitude2);

        Assert.assertEquals(1140.304264507215, distance, 0.01);
    }

}