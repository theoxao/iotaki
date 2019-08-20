import com.theoxao.FutureResultHandler
import com.theoxao.LileepShell
import org.junit.Test

/**
 * @author theo
 * @date 19-8-19
 */
class SimpleTest {

    @Test
    fun testListInsert() {
        val list = arrayListOf<Int>(1, 2, 3, 4, 5)
        list.add(2, 6)
        println(list.joinToString(","))
    }

}