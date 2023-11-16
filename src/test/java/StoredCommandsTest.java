import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoredCommandsTest {
    StoredCommands storedCommands;

    @BeforeEach
    public void setUp() {
        storedCommands = new StoredCommands();
    }

    @Test
    public void store_a_command_in_the_list() {
        storedCommands.store("Create checking 333");

        boolean actual = storedCommands.containsCommand("Create checking 333");
        assertTrue(actual);
    }
}
