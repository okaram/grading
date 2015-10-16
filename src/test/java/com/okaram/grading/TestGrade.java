package com.okaram.grading;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import com.okaram.grading.Grade;
import com.okaram.grading.GradingListener;


public class TestGrade {
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

/*
    public static void main(String[] args) {
        JUnitCore core= new JUnitCore();
        core.addListener(new GradingListener());
        core.run(TestGrade.class);
    }
*/    
}
