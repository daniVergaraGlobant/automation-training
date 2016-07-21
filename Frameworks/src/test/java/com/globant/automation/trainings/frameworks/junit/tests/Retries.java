package com.globant.automation.trainings.frameworks.junit.tests;

import com.globant.automation.trainings.frameworks.junit.retry.Retry;
import com.globant.automation.trainings.frameworks.junit.retry.RetryRule;
import org.junit.Rule;
import org.junit.Test;
import org.testng.Assert;

/**
 * Created by Juan Krzemien on 7/21/2016.
 */
public class Retries {

    @Rule
    public RetryRule rule = new RetryRule(3);

    @Test
    @Retry
    public void testToRetry() {
        Assert.assertEquals(1, 0);
    }

}