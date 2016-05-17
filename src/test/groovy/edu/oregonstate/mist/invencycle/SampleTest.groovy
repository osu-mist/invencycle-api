package edu.oregonstate.mist.invencycle

import edu.oregonstate.mist.invencycle.core.Sample
import org.junit.Test
import static org.junit.Assert.*

class SampleTest {
    @Test
    public void testSample() {
        assertTrue(new Sample().message == 'hello world')
    }
}
