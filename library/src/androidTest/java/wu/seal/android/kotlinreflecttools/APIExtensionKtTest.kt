package wu.seal.android.kotlinreflecttools

import android.support.test.runner.AndroidJUnit4
import com.winterbe.expekt.should
import org.junit.Test
import org.junit.runner.RunWith

/** API Extension API test
 * Created by Seal.Wu on 2018/1/10.
 */
@RunWith(AndroidJUnit4::class)
class APIExtensionKtTest {

    @Test
    fun getPropertyValue() {
        val demo = TestDemo()
        val nameValue = demo.getPropertyValue("name")
        nameValue.should.be.equal("seal")
    }

    @Test
    fun changePropertyValue() {
        val demo = TestDemo()
        val originValue = demo.age
        demo.changePropertyValue("age", 100)
        val nowValue = demo.age
        originValue.should.not.equal(nowValue)
        nowValue.should.be.equal(100)
    }

    @Test
    fun changePropertyValueByPropertyReference() {
        val demo = TestDemo()
        val originValue = demo.age
        demo.changePropertyValueByPropertyReference(demo::age, 100)
        val nowValue = demo.age
        originValue.should.not.equal(nowValue)
        nowValue.should.be.equal(100)
    }

    @Test
    fun invokeMethod() {
        val demo = TestDemo()
        val methodResult = demo.invokeMethod("isMan") as Boolean
        methodResult.should.be.`true`
    }

    @Test
    fun changeValue() {
        val demo = TestDemo()
        demo::age.changeValue(demo, 100)
        demo.age.should.be.equal(100)
    }

    @Test
    fun packageLevelGetPropertyValueByName() {
        val topAge = ::topNameWu.packageLevelGetPropertyValueByName("topAge")
        topAge.should.be.equal(666)
    }


    @Test
    fun packageLevelGetPropertyValueByName1() {
        val topAge = ::funDoubleAge.packageLevelGetPropertyValueByName("topAge")
        topAge.should.be.equal(666)
    }

    @Test
    fun packageLevelChangePropertyValue() {
        ::topName.packageLevelChangePropertyValue("sealwu")
        topName.should.be.equal("sealwu")
    }

    @Test
    fun packageLevelChangeOtherPropertyValueByName() {
        ::topName.packageLevelChangeOtherPropertyValueByName("topNameWu", "seal")
        ::topNameWu.packageLevelGetPropertyValueByName("topNameWu").should.be.equal("seal")
    }

    @Test
    fun packageLevelChangeOtherPropertyValueByName1() {
        ::funDoubleAge.packageLevelChangeOtherPropertyValueByName("topNameWu", "seal")
        ::topNameWu.packageLevelGetPropertyValueByName("topNameWu").should.be.equal("seal")
    }

    @Test
    fun packageLevelInvokeMethodByName() {
        val methodResult = ::topName.packageLevelInvokeMethodByName("gotIt") as Boolean
        methodResult.should.be.`true`
    }

    @Test
    fun packageLevelInvokeMethodByName1() {
        val methodResult = ::funDoubleAge.packageLevelInvokeMethodByName("gotIt") as Boolean
        methodResult.should.be.`true`
    }

}