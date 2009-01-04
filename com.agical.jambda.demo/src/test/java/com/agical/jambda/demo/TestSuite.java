package com.agical.jambda.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.agical.bumblebee.collector.BumblebeeCollectors;
import com.agical.bumblebee.junit4.BumbleBeeSuiteRunner;
import com.agical.bumblebee.ruby.RubyCollector;
import com.agical.jambda.demo.practical.PracticalUsages;


@RunWith(BumbleBeeSuiteRunner.class)
@BumblebeeCollectors({RubyCollector.class})
@SuiteClasses({UsingFunctions.class, 
                UsingOptions.class, 
                UsingUnions.class, 
                UsingSequence.class, 
                PracticalUsages.class, 
                Euler.class})
public class TestSuite {
    /*!!
    #{configuration.version_nr=File.new('../buildfile').read.match('VERSION_NUMBER = \'(.*)\'')[1];''}
    #{configuration.target_file='target/site/index.html';''}
    #{configuration.default_target_file=configuration.target_file;''}
    #{configuration.copyright='Johan Kullbom, Daniel Brolund, Joakim Ohlrogge';''}
    #{configuration.inception_year=2008;''}
    #{configuration.document_title='The Jambda ' + configuration.version_nr + ' Documentation';''}
    #{set_header 'The Jambda documentation';''}
    
    Jambda is an attempt to provide the Java(TM) world with tools and concepts 
    from *functional programming* (FP). 
    
    The goals are several:
       - To provide Java programmers with expressive FP constructs
       - To provide a bridge for Java programmers into the FP-world
       - To see how far Java and generics can be stretched
    
    This document is an attempt to introduce Java programmers into the FP world,
    and at the same time explain some (or most) of the features in Jambda.
    */
    
    
}
