package com.okaram.grading;

public class Utilities {
    public static Grade getGradeForMethod(String className, String methodName) throws Exception
    {
     //   try {
            Class c=Class.forName(className);
            java.lang.reflect.Method m=c.getMethod(methodName);
            
            return m.getAnnotation(Grade.class);
       // } catch(Exception e) {
       //     return null;
       // }
        

        
    }
}
