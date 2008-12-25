package com.agical.jambda.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.agical.bumblebee.collector.BumblebeeCollectors;
import com.agical.bumblebee.junit4.BumbleBeeSuiteRunner;
import com.agical.bumblebee.ruby.RubyCollector;


@RunWith(BumbleBeeSuiteRunner.class)
@BumblebeeCollectors({RubyCollector.class})
@SuiteClasses({UsingFunctions.class, UsingOptions.class, UsingUnions.class, UsingCons.class})
public class TestSuite {

}
