package com.okaram.grading;

import org.junit.runner.notification.*;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import java.text.MessageFormat;

public class GradingListener extends RunListener {
    protected int ranPoints=0, failedPoints=0, ignoredPoints=0, gradedTests=0, ungradedTests=0;
    boolean printMessages; 

    GradingListener(boolean printMessages) {
        ranPoints=0;
        failedPoints=0;
        ignoredPoints=0;
        gradedTests=0;
        ungradedTests=0;
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
            if(printMessages)
                System.out.println(MessageFormat.format("Test {0} failed (no points assigned)",f.getDescription().getDisplayName()));
        }
        else {
            failedPoints+=g.points();
            if(printMessages)
                System.out.println(
                    MessageFormat.format(
                        "Test {0} failed (0 out of {1} points)",
                        f.getDescription().getDisplayName(),
                        g.points()
                    )
                );
        }
    }

    @Override
    public void testIgnored(Description d) {
        Grade g=d.getAnnotation(Grade.class);
        if(g==null)  {// test was not graded
            ungradedTests++;
            if(printMessages)
                System.out.println(MessageFormat.format("Test {0} ignored (no points assigned)",d.getDisplayName()));
        }
        else {
            ignoredPoints+=g.points();
            if(printMessages)
                System.out.println(
                    MessageFormat.format(
                        "Test {0} ignored ({1} points)",
                        d.getDisplayName(),
                        g.points()
                    )
                );
        }
    }

    @Override
    public void testFinished(Description d)
    {
        Grade g=d.getAnnotation(Grade.class);
        if(g==null) {
            gradedTests++;
            if(printMessages)
                System.out.println(MessageFormat.format("Test {0} finished (no points assigned)",d.getDisplayName()));
        }else {
            gradedTests++;
            ranPoints+=g.points();
            
            if(printMessages) {
                System.out.println(
                    MessageFormat.format(
                        "Test {0} finished ({1} points)",
                        d.getDisplayName(),
                        g.points()
                    )
                );
            }
        }
    }

    public static GradingListener gradeTestsForClasses(String classNames[], boolean printMessages) throws ClassNotFoundException
    {
        JUnitCore core=new JUnitCore();
        GradingListener l=new GradingListener(true);
        core.addListener(l);

        for(String className : classNames) {
            Class theClass=Class.forName(className); 
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
        System.out.println(
                MessageFormat.format("Graded {0} tests. Got {1} out of {2} points ({3} ignored)",
                        l.getGradedTests(),l.getRanPoints()-l.getFailedPoints(), l.getRanPoints(), l.getIgnoredPoints()
                )
        ); 
    }
}
