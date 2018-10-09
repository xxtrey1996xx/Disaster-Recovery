import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void main() {
    }

    @Test
    void distance() {
        assertEquals(3.782939,Main.distance(39.76,-94.94,39.75,-94.87,true),0.1);
    }

    @Test
    void toMiles() {
        assertEquals(9.32056788,Main.toMiles(15),0.01);
    }

}