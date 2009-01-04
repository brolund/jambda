package com.agical.jambda.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ChurchBooleans {
    
    public static abstract class CBool {
        public abstract <T> T cIf(T cThen, T cElse);
        
        public CBool cOr(CBool other) { return this.cIf(this, other); }
        public CBool cAnd(CBool other) { return this.cIf(other, CFalse); }
        public CBool cNot() { return this.cIf(CFalse, CTrue); }
        
        public static CBool CTrue = 
            new CBool() { public <T> T cIf(T cThen, T cElse) { return cThen; } };
        
        public static CBool CFalse =
            new CBool() { public <T> T cIf(T cThen, T cElse) { return cElse; } };
    }
    
    @Test
    public void churchBooleans() throws Exception {
        CBool cond = CBool.CTrue;
        
        Integer shouldBeOne = cond.cAnd(CBool.CTrue).cIf(1, 2);
        Integer shouldBeTwo = cond.cAnd(CBool.CFalse).cIf(1, 2);
        
        assertEquals(new Integer(1), shouldBeOne);
        assertEquals(new Integer(2), shouldBeTwo);
    }
}
