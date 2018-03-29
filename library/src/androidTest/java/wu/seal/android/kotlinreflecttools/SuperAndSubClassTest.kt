package wu.seal.android.kotlinreflecttools

import android.support.test.runner.AndroidJUnit4
import com.winterbe.expekt.should
import org.junit.Test
import org.junit.runner.RunWith


/**
Created By: Seal.Wu
Date: 2018/3/29
Time: 10:43
 */
@RunWith(AndroidJUnit4::class)
class SuperAndSubClassTest {

    @Test
    fun getSuperClassFieldValueTest() {
        val obj = SubClass()
        obj.getPropertyValue("superFieldOne").should.be.equal("superFieldOne")
    }

    @Test
    fun invokeSuperClassMethodTest() {
        val obj = SubClass()
        obj.invokeMethod("superPrivateFun" ,true).should.be.equal(true)
    }

    @Test
    fun invokeCommonNameFunInSubClass() {
        val obj = SubClass()
        obj.invokeMethod("getSignString").should.be.equal("Sub")
    }

    @Test
    fun changeSuperClassFieldValue() {
        val obj = SubClass()
        val newValue = "newValue"
        obj.changePropertyValue("superFieldOne",newValue)
        obj.getPropertyValue("superFieldOne").should.be.equal(newValue)
    }
}