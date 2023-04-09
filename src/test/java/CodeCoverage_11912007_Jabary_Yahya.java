import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeCoverage_11912007_Jabary_Yahya {

    private String func(boolean cond1, boolean cond2) {
        String out = "";
        if (cond1) {
            out += "cond1";
        }
        if (cond2) {
            out += "cond2";
        }
        return out;
    }

    // C0 Statement coverage ::
    @Test
    void testFunc_C0() {
        assertEquals("cond1cond2", func(true, true));
    }
    // :: C0 Statement coverage

    // C1 Branch coverage ::
    @Test
    void testFunc_C1() {
        assertEquals("cond1cond2", func(true, true));
        assertEquals("", func(false, false));
    }
    // :: C1 Branch coverage

    // C2 Simple condition coverage ::
    @Test
    void testFunc_C2simple() {
        assertEquals("cond1cond2", func(true, true));
        assertEquals("", func(false, false));
    }
    // :: C2 Simple condition coverage

    // C2 Multiple condition coverage ::
    @Test
    void testFunc_C2multiple() {
        assertEquals("", func(false, false));
        assertEquals("cond2", func(false, true));
        assertEquals("cond1", func(true, false));
        assertEquals("cond1cond2", func(true, true));
    }
    // :: C2 Multiple condition coverage

    // C3 Path coverage ::
    @Test
    void testFunc_C3() {
        assertEquals("", func(false, false));
        assertEquals("cond2", func(false, true));
        assertEquals("cond1", func(true, false));
        assertEquals("cond1cond2", func(true, true));
    }
    // :: C3 Path coverage
}
