import kotlin.test.Test
import kotlin.test.assertEquals

class ModelTest {
    @Test
    fun oneAceHardHand() {
        assertEquals(18, hand(10, 11, 7).score())
    }

    @Test
    fun oneAceSoftHand() {
        assertEquals(17, hand(11, 2, 4).score())
    }

    @Test
    fun twoAcesSoftHand() {
        assertEquals(12, hand(11, 11).score())
    }

    @Test
    fun twoAcesSoftHand2() {
        assertEquals(16, hand(11, 11, 4).score())
    }

    @Test
    fun twoAcesHardHand() {
        assertEquals(17, hand(11, 11, 5, 10).score())
    }

    private fun hand(vararg values:Int): Hand = Hand("test hand",
        values.map { Card(it, "some card") }.toMutableList())
}