import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import jdk.jfr.Timestamp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

public class TestCardGame {
    private CardGame cardGame;

    @Before
    public final void setUp() throws Exception {

        cardGame.setPlayerID(1);

        // Instantiates a School instance;
        school = new School();
        school.setName("BVB School");
        school.setAddress("BVB School");
        school.setContactNo("0778563251");
        school.setFaxNo("0778563251");
        school.setWebsite("javaguides.new");
        school.setStartedDate(date);
        school.setModifiedTime(date);        
    }

    @Test
    public void testInputNumPlayers () {
        CardGame tester = new CardGame();
        assertEquals();
    }
}
