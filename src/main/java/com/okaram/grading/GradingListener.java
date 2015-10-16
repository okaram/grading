package com.okaram.grading;

import org.junit.runner.notification.*;
import org.junit.runner.Description;

public class GradingListener extends RunListener {
    int ranPoints=0, failedPoints=0, ignoredPoints=0, gradedTests=0, ungradedTests=0;
    @Override
    public void testFailure(Failure f) {
//        System.out.println("Failure !");
        Grade g=f.getDescription().getAnnotation(Grade.class);
        if(g==null)  {// test was not graded
            ungradedTests++;
        }
        else {
            failedPoints+=g.points();
        }
    }

    @Override
    public void testFinished(Description d)
    {
        Grade g=d.getAnnotation(Grade.class);
        if(g==null) {
            ungradedTests++;
        }else {
            gradedTests++;
            ranPoints+=g.points();
        }
    }

    @Override
    public void testRunFinished(org.junit.runner.Result r) {
        System.out.println("Graded "+gradedTests +" tests. Points- total: "+ranPoints+" failed: " +failedPoints+" ignored: "+ignoredPoints);
    }
}
