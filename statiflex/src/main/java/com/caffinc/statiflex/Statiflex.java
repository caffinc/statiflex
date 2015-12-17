package com.caffinc.statiflex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


/**
 * Uses reflection to modify static final fields.
 * User under extreme duress.
 *
 *                 uuuuuuu
 *             uu$$$$$$$$$$$uu
 *          uu$$$$$$$$$$$$$$$$$uu
 *         u$$$$$$$$$$$$$$$$$$$$$u
 *        u$$$$$$$$$$$$$$$$$$$$$$$u
 *       u$$$$$$$$$$$$$$$$$$$$$$$$$u
 *       u$$$$$$$$$$$$$$$$$$$$$$$$$u
 *       u$$$$$$"   "$$$"   "$$$$$$u
 *       "$$$$"      u$u       $$$$"
 *        $$$u       u$u       u$$$
 *        $$$u      u$$$u      u$$$
 *         "$$$$uu$$$   $$$uu$$$$"
 *          "$$$$$$$"   "$$$$$$$"
 *            u$$$$$$$u$$$$$$$u
 *             u$"$"$"$"$"$"$u
 *  uuu        $$u$ $ $ $ $u$$       uuu
 * u$$$$        $$$$$u$u$u$$$       u$$$$
 *  $$$$$uu      "$$$$$$$$$"     uu$$$$$$
 *u$$$$$$$$$$$uu    """""    uuuu$$$$$$$$$$
 *$$$$"""$$$$$$$$$$uuu   uu$$$$$$$$$"""$$$"
 * """      ""$$$$$$$$$$$uu ""$"""
 *           uuuu ""$$$$$$$$$$uuu
 *  u$$$uuu$$$$$$$$$uu ""$$$$$$$$$$$uuu$$$
 *  $$$$$$$$$$""""           ""$$$$$$$$$$$"
 *   "$$$$$"                      ""$$$$""
 *     $$$"                         $$$$"
 *
 * @author Sriram
 */
public class Statiflex
{
    private static final Logger LOG = LoggerFactory.getLogger( Statiflex.class );


    /**
     * Private constructor, no instantiation
     */
    private Statiflex()
    {
    }


    /**
     * Changes the value of a private static final field in a class
     * Note: Compiler optimization might inline static final fields which will break this, use with caution
     * @param clazz Class to modify
     * @param fieldName Field to modify
     * @param value Value to set
     * @return true if set succeeded, false otherwise
     * @throws NoSuchFieldException Thrown if the field specified was not found
     */
    public static boolean flex( Class clazz, String fieldName, Object value ) throws NoSuchFieldException
    {
        return flex( clazz.getDeclaredField( fieldName ), value );
    }


    /**
     * Changes the value of a private static final field
     * @param field Field to modify
     * @param value Value to set
     * @return true if set succeeded, false otherwise
     */
    private static boolean flex( Field field, Object value )
    {
        try {
            Field modifiersField = Field.class.getDeclaredField( "modifiers" );
            boolean isModifierAccessible = modifiersField.isAccessible();
            modifiersField.setAccessible( true );
            modifiersField.setInt( field, field.getModifiers() & ~Modifier.FINAL );

            boolean isAccessible = field.isAccessible();
            field.setAccessible( true );
            field.set( null, value );

            field.setAccessible( isAccessible );
            modifiersField.setAccessible( isModifierAccessible );
            return true;
        } catch ( IllegalAccessException e ) {
            LOG.error( "Could not access field {}", field.getName(), e );
        } catch ( NoSuchFieldException e ) {
            LOG.error( "Could not find field, cannot modify value", e );
        }
        return false;
    }
}
