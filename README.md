# statiflex ![Travis CI](https://travis-ci.org/caffinc/statiflex.svg?branch=master)
### Change static final values in Java


## Introduction
Have you ever created any classes with `static final` fields in Java? And then later found it impossible to test because different tests need different values in the fields? Or do you want your tests to have a whole other set of values that are different from your production values?

Your first solution is to use different property files and apply `Maven`'s profiles to get the correct property file for the correct profile. But this is not always easy, especially when different tests might need different values to be stored in the `static final` fields.

Introducing `statiflex` - a lightweight library that lets you modify your `static final` fields for your tests!

## Usage
Call the `Statiflex.flex` method with the first parameter being the *class* whose static final member you want to modify, the *field name* you want to modify as a String, and the *value* you want to modify it to:

    Statiflex.flex( DummyClass.class, "DUMMY_FIELD", "NEW VALUE" );

## Maven Dependency
Statiflex is available on Bintray and Maven Central:

    <dependency>
        <groupId>com.caffinc</groupId>
        <artifactId>statiflex</artifactId>
        <version>1.0.3</version>
    </dependency>

## When does Statiflex fail?
`Statiflex` will not work on compiler optimized fields. The following example will detail that:

Suppose you have a class `DummyClass`:

    class DummyClass
    {
        private static final String DUMMY_FIELD = Properties.getDummyValue();
        private static final String DUMMY_FIELD_2;
        private static final String DUMMY_FIELD_3 = "Test";
        static {
            DUMMY_FIELD_2 = "Test";
        }
    }

Fields `DUMMY_FIELD` and `DUMMY_FIELD_2` are not optimized by the compiler, but `DUMMY_FIELD_3` is. When the compiler sees `DUMMY_FIELD_3` used in the code elsewhere, it replaces it with `"Test"`, so `Statiflex` will not be able to change the value of the field as the field isn't actually used in the code anymore.

Fields `DUMMY_FIELD` is populated via an external function call, and `DUMMY_FIELD_2` is populated in the static field. Both these fields can be modified by calling:

    Statiflex.flex( DummyClass.class, "DUMMY_FIELD", "Some other String" );
    Statiflex.flex( DummyClass.class, "DUMMY_FIELD_2", "Some other String" );

## Warnings
**DO NOT USE STATIFLEX IN MISSION CRITICAL APPLICATIONS.**

Statiflex uses Reflection. Reflection is that part of Java which scares off new developers who don't know enough about it, and experienced developers wield it with awe. The more you learn about it, the more you hear that nothing in the Reflection library is guaranteed to work as written on the box, and no one should really use it.

Some people state performance to be the issue, some people say the problem is with Reflection doing some really weird low level stuff that goes against language principles. They're all *probably* right. But there's no denying that Reflection comes in handy in several places, providing a solution where there is no cleaner, or easier way to do things.

If you have a class with `static final` fields that need to be changed for tests, see if using `Maven`'s profile system is an option. Failing that, see if it's OK to remove the `final` modifier. After that see if it's alright to change your tests in such a way that they work with the `static final` fields the way they are.

Before you use Statiflex, think long and hard about what you're about to do. You will be changing the very definition of `final`, and that is definitely not a good thing.

## Help
I can't help you if Statiflex broke everything in your project. Send me an email at [admin@caffinc.com](mailto:admin@caffinc.com) if you think that Statiflex should be changing a value but isn't, after making sure that you've checked out the `When does Statiflex fail?` section.