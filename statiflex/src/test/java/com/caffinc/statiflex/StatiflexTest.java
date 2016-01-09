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
        private static final String DUMMY_FIELD;
        private static final String DUMMY_FIELD_2 = getDummyValue();

        static {
            DUMMY_FIELD = "TEST";
        }

        private static String getDummyValue()
        {
            return "TEST";
        }
    }


    @Test
    public void testFlexOnStaticBlock() throws Exception
    {
        Assert.assertEquals( "private static final value should be TEST", "TEST", DummyClass.DUMMY_FIELD );
        Statiflex.flex( DummyClass.class, "DUMMY_FIELD", "TESTED" );
        Assert.assertEquals( "private static final value should be TESTED after flexing", "TESTED", DummyClass
            .DUMMY_FIELD );
    }


    @Test
    public void testFlexOnStaticMethodValue() throws Exception
    {
        Assert.assertEquals( "private static final value should be TEST", "TEST", DummyClass.DUMMY_FIELD_2 );
        Statiflex.flex( DummyClass.class, "DUMMY_FIELD_2", "TESTED" );
        Assert.assertEquals( "private static final value should be TESTED after flexing", "TESTED", DummyClass
            .DUMMY_FIELD_2 );
    }
}
