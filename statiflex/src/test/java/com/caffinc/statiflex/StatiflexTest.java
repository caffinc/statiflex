package com.caffinc.statiflex;

import org.junit.Assert;
import org.junit.Test;


/**
 * Tests the Statiflex class
 * @author Sriram
 */
public class StatiflexTest
{
    static class DummyClass
    {
        private static final String DUMMY_FIELD = getDummyValue();


        private static String getDummyValue()
        {
            return "TEST";
        }
    }


    @Test
    public void testFlex() throws Exception
    {
        Assert.assertEquals( "private static final value should be TEST", "TEST", DummyClass.DUMMY_FIELD );
        Statiflex.flex( DummyClass.class, "DUMMY_FIELD", "TESTED" );
        Assert.assertEquals( "private static final value should be TESTED after flexing", "TESTED", DummyClass
            .DUMMY_FIELD );
    }
}
