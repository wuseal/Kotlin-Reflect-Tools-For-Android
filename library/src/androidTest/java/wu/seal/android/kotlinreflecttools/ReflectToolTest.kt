package wu.seal.android.kotlinreflecttools


/**
Created By: Seal.Wu
Date: 2017/11/14
Time: 18:31
 */
import android.support.test.runner.AndroidJUnit4
import com.winterbe.expekt.should
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.jvm.internal.CallableReference

/**
 * test case
 * Created by Seal.Wu on 2017/10/27.
 */
@RunWith(AndroidJUnit4::class)
class ReflectToolsKtTest {
    @org.junit.Before
    fun setUp() {

    }

    @org.junit.After
    fun tearDown() {
        /**
         * blow recover the original value of the properties
         */
        changeTopPropertyValue(::topName, "topSeal")
        changeTopPropertyValueByName(::topName as CallableReference, "topAgeName", "666")
        changeTopPropertyValueByName(::topName as CallableReference, "topAge", 666)

    }

    @org.junit.Test
    fun changeTopPropertyValue() {
        val targetName = "fashionSeal"
        assertNotEquals(targetName, topName)
        changeTopPropertyValue(::topName, targetName)
        assertEquals(targetName, topName)
    }

    @org.junit.Test
    fun changeClassPropertyValue() {
        val demoObj = TestDemo()
        val preAge = demoObj.age
        changeClassPropertyValue(demoObj, demoObj::age, preAge + 1)
        assertNotEquals(preAge, demoObj.age)
    }

    @org.junit.Test
    fun changeClassPropertyValueByName() {
        val demoObj = TestDemo()
        val preAge = demoObj.age
        changeClassPropertyValueByName(demoObj, "age", preAge + 1)
        assertNotEquals(preAge, demoObj.age)

        val newValue = "newSeal"
        changeClassPropertyValueByName(demoObj, "name", newValue)
        assertEquals(newValue, getClassPropertyValueByName(demoObj, "name"))
    }

    @org.junit.Test
    fun changeTopPropertyValueByName() {

        val targetName = "fashionSeal"
        assertNotEquals(targetName, topName)
        changeTopPropertyValueByName(::topName as CallableReference, "topName", targetName)
        assertEquals(targetName, topName)

        val targetAgeName = "newName"
        assertNotEquals(targetAgeName, getTopPropertyValueByName(::topName as CallableReference, "topAgeName"))
        changeTopPropertyValueByName(::topName as CallableReference, "topAgeName", targetAgeName)
        assertEquals(targetAgeName, getTopPropertyValueByName(::topName as CallableReference, "topAgeName"))

        val targetAge = 18
        assertNotEquals(targetAge, getTopPropertyValueByName(::topName as CallableReference, "topAge"))
        changeTopPropertyValueByName(::topName as CallableReference, "topAge", targetAge)
        assertEquals(targetAge, getTopPropertyValueByName(::topName as CallableReference, "topAge"))
    }

    @org.junit.Test
    fun getClassPropertyValueByName() {
        val expectedNamePropertyValue = "seal"
        val demoObj = TestDemo()
        val getClassPropertyValue = getClassPropertyValueByName(demoObj, "name")
        assertEquals(expectedNamePropertyValue, getClassPropertyValue)
    }

    @org.junit.Test
    fun getTopPropertyValueByName() {
        val expectedTopNamePropertyValue = "topSeal"
        val getTopPropertyValue = getTopPropertyValueByName(::funDoubleAge as CallableReference, "topName")
        assertEquals(expectedTopNamePropertyValue, getTopPropertyValue)

        val expectedTopAgeName = "666"
        val getTopAgeNamePropertyValue = getTopPropertyValueByName(::funDoubleAge as CallableReference, "topAgeName")
        assertEquals(expectedTopAgeName, getTopAgeNamePropertyValue)

        val expectedTopAge = 666
        val getTopAgePropertyValue = getTopPropertyValueByName(::funDoubleAge as CallableReference, "topAge")
        assertEquals(expectedTopAge, getTopAgePropertyValue)

    }

    @org.junit.Test
    fun invokeMethodByMethodName() {
        val demoObj = TestDemo()
        val expectedObjMethodValue = true
        val getMethodValue = invokeClassMethodByMethodName(demoObj, "isMan")

        assertEquals(expectedObjMethodValue, getMethodValue)

    }

    @org.junit.Test
    fun invokeTopMethodByMethodNameTest() {
        val expectedPreTopAge = 665
        val expectedNextTopAge = 667

        val invokeMethodPreTopAgeValue = invokeTopMethodByMethodName(::topName as CallableReference, "preTopAge")
        val invokeMethodNextTopAgeValue = invokeTopMethodByMethodName(::topName as CallableReference, "nextTopAge")

        assertEquals(expectedPreTopAge, invokeMethodPreTopAgeValue)
        assertEquals(expectedNextTopAge, invokeMethodNextTopAgeValue)


        val testAge = 10
        val invokeMethodDoubleAge = invokeTopMethodByMethodName(::topName as CallableReference, "doubleAgeAndPrint", *arrayOf(testAge))
        assertEquals(testAge * 2, invokeMethodDoubleAge)

        val testAge1 = 10
        val testAg2 = 13
        val invokeMethodPlusAge = invokeTopMethodByMethodName(::topName as CallableReference, "plusTwoAge", testAge1, testAg2)
        assertEquals(testAge1 + testAg2, invokeMethodPlusAge)


        val testName = "Seal"
        val testAg3 = 18
        val invokeMethodPlusNameAge = invokeTopMethodByMethodName(::topName as CallableReference, "plusNameAndAge", testName, testAg3)
        assertEquals(testName + testAg3, invokeMethodPlusNameAge)
    }

    @Test
    fun invokeMethodByMethodNameWithDifferentArguments() {
        val demoObj = TestDemo()
        val expectedObjMethodValue = false
        val getMethodValue = invokeClassMethodByMethodName(demoObj, "isMan", expectedObjMethodValue)
        getMethodValue.should.be.equal(expectedObjMethodValue)

        val args = 0.1
        val getOtherTypeArgMethodValue = invokeClassMethodByMethodName(demoObj, "isMan", args) as Boolean
        getOtherTypeArgMethodValue.should.be.`false`

    }

    @Test
    fun invokeMethodByMethodNameWithWrongArguments() {
        val demoObj = TestDemo()
        val expectedObjMethodValue = false

        val returnValue = try {
            invokeClassMethodByMethodName(demoObj, "isMan", "") as Boolean?
        } catch (e: Exception) {
            false
        }
        expectedObjMethodValue.should.be.equal(returnValue)

    }
}