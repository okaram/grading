package com.okaram.grading;

import org.junit.runner.notification.*;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;


public class GradingListener extends RunListener {
    protected int ranPoints=0, failedPoints=0, ignoredPoints=0, gradedTests=0, ungradedTests=0;
    boolean printMessages; // TODO: make it do something useful :)

    GradingListener(boolean printMessages) {
        this.printMessages=printMessages;
    }

    public int getRanPoints() { return ranPoints; }
    public int getFailedPoints() { return failedPoints; }
    public int getIgnoredPoints() { return ignoredPoints; }
    public int getGradedTests() {return gradedTests;}
    public int getUngradedTests() {return ungradedTests;}

    @Override
    public void testFailure(Failure f) {
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

    public static GradingListener gradeTestsForClasses(String classNames[], boolean printMessages) throws ClassNotFoundException
    {
        JUnitCore core=new JUnitCore();
        GradingListener l=new GradingListener(printMessages);
        core.addListener(l);

        for(String className : classNames) {
            Class theClass=Class.forName(className); // catch exception :)
            core.run(theClass);
        }

        return l;
    }

    
    public static void main(String args[]) throws ClassNotFoundException
    {
        if(args.length==0) 
            System.out.println("No classes specified");{
        } 

        GradingListener l=gradeTestsForClasses(args, true);
        System.out.println(""); // print something useful
    }
}
