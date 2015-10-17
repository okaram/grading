package com.okaram.grading.ignore;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import com.okaram.grading.Grade;
import com.okaram.grading.GradingListener;


public class TestWithSucceedAndFail {

    @Test
    @Grade(points=10)
    public void testSucceed()
    {

    }

    @Test
    @Grade(points=5)
    public void testFail()
    {
        Assert.assertTrue(false);
    }
}
