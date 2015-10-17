package com.okaram.grading;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import com.okaram.grading.Grade;
import com.okaram.grading.GradingListener;

import java.util.Arrays;


public class TestGrade {
/*    static class InnerTest {
        @Test
        @Grade(points=5)
        public void innerTestSucceed(){}
    }
*/
    @Test
    public void testOneClass() throws ClassNotFoundException
    {
        String[] classes= {
                "com.okaram.grading.ignore.TestWithSucceedAndFail"
        };
        GradingListener l=GradingListener.gradeTestsForClasses(classes,false);
        Assert.assertEquals(l.getFailedPoints(),5);
        Assert.assertEquals(l.getGradedTests(),2);
    }

    @Test
    public void testTwiceSameClass() throws ClassNotFoundException
    {
        String[] classes= {
                "com.okaram.grading.ignore.TestWithSucceedAndFail",
                "com.okaram.grading.ignore.TestWithSucceedAndFail"
        };
        GradingListener l=GradingListener.gradeTestsForClasses(classes,false);
        Assert.assertEquals(l.getFailedPoints(),10);
        Assert.assertEquals(l.getGradedTests(),4);
    }

}
