package com.okaram.grading.ignore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Assert;
import org.junit.Test;

import com.okaram.grading.Grade;
import com.okaram.grading.GradingListener;

/**
 * Created by Orlando on 10/16/2015.
 */
public class TestWithIgnores {
    @Ignore
    @Grade(points=10)
    @Test
    public void testIgnored() {}

    @Test
    @Grade(points=10)
    public void testSucceed() {}
}
