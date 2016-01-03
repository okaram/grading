package com.okaram.grading;

import org.junit.runner.notification.*;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import java.text.MessageFormat;

public class GradingListener extends RunListener {
    protected int ranPoints=0, failedPoints=0, ignoredPoints=0, gradedTests=0, ungradedTests=0;
    boolean printMessages; // TODO: make it do something useful :)

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
                System.out.println(MessageFormat.format("Test {} failed (no points assigned)",f.getDescription().getDisplayName()));
        }
        else {
            if(printMessages)
                System.out.println(
                    MessageFormat.format(
                        "Test {} failed (0 out of {} points)",
                        f.getDescription().getDisplayName(),
                        g.points()
                    )
                );
            failedPoints+=g.points();
        }
    }

    @Override
    public void testIgnored(Description d) {
        Grade g=d.getAnnotation(Grade.class);
        if(g==null)  {// test was not graded
            ungradedTests++;
            if(printMessages)
                System.out.println(MessageFormat.format("Test {} ignored (no points assigned)",d.getDisplayName()));
        }
        else {
            if(printMessages)
                System.out.println(
                    MessageFormat.format(
                        "Test {} ignored ({} points)",
                        d.getDisplayName(),
                        g.points()
                    )
                );
            ignoredPoints+=g.points();
        }
    }

    @Override
    public void testFinished(Description d)
    {
        Grade g=d.getAnnotation(Grade.class);
        if(g==null) {
            if(printMessages)
                System.out.println(MessageFormat.format("Test {} finished (no points assigned)",d.getDisplayName()));
            gradedTests++;
        }else {
            if(printMessages)
                System.out.println(
                    MessageFormat.format(
                        "Test {} finished ({} points)",
                        d.getDisplayName(),
                        g.points()
                    )
                );
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
        System.out.println(
                MessageFormat.format("Graded {0} tests. Got {1} out of {2} points ({3} ignored)",
                        l.getGradedTests(),l.getRanPoints()-l.getFailedPoints(), l.getRanPoints(), l.getIgnoredPoints()
                )
        ); // print something useful
    }
}
