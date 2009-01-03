package com.agical.jambda.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.agical.bumblebee.collector.BumblebeeCollectors;
import com.agical.bumblebee.junit4.BumbleBeeSuiteRunner;
import com.agical.bumblebee.ruby.RubyCollector;
import com.agical.jambda.demo.practical.PracticalUsages;


@RunWith(BumbleBeeSuiteRunner.class)
@BumblebeeCollectors({RubyCollector.class})
@SuiteClasses({UsingFunctions.class, UsingOptions.class, UsingUnions.class, UsingCons.class, PracticalUsages.class})
public class TestSuite {

}
