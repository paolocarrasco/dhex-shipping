import org.junit.Test;

import static org.junit.Assert.*;

public class GreetingGeneratorTest {

    @Test
    public void testThatGreetsTheWorld() throws Exception {
        GreetingGenerator greetingGenerator = new GreetingGenerator();
        String greeting = greetingGenerator.generateGreetingTo("world");
        assertEquals("hello world", greeting);
    }

}