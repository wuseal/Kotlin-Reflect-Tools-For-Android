[![Bintray](https://img.shields.io/bintray/v/wusealking/maven/wu.seal.android.kotlinreflecttools.svg)](https://bintray.com/wusealking/maven/wu.seal.android.kotlinreflecttools#)
[![GitHub stars](https://img.shields.io/github/stars/wuseal/Kotlin-Reflect-Tools-For-Android.svg?style=social&label=Stars&style=plastic)](https://github.com/wuseal/Kotlin-Reflect-Tools-For-Android/stargazers)
[![license](https://img.shields.io/github/license/wuseal/Kotlin-Reflect-Tools-For-Android.svg)](https://github.com/wuseal/Kotlin-Reflect-Tools-For-Android/blob/master/LICENSE)
# Kotlin-Reflect-Tools-For-Android
Kotlin reflect tools for Android

Related Project: [Kotlin-Reflect-Tools-For-JVM](https://github.com/wuseal/Kotlin-Reflect-Tools-For-JVM)

## OverView
This is a tool library for Kotlin to use java reflect APIs in Kotlin simply method on android platform. It can modify or read the top level private visible property value in Kotlin way.
 
 ## Usage
 * Add jcenter repository in your moduel build gradle:
   ```groovy
    repositories {
         jcenter()
    }
    ```
    
 * Apply library in dependency config:
 
    ```groovy
       compile 'wu.seal:kotlin-reflect-tools-for-android:1.1.2'
    ```
    
## APIs

|Method         | Describe          |
|:------------- |:-------------|
|Any.getPropertyValue(propertyName: String): Any?|get object property value by name|
|Any.changePropertyValue(propertyName: String, newValue: Any?) |change object property value by name|
|Any.changePropertyValueByPropertyReference(kProperty: KProperty<R>, newValue: Any?)|change object property value by property reference|
|Any.invokeMethod(methodName: String, vararg args: Any?): Any?|invoke a method through object by method name|
|<R> KProperty<R>.changeValue(thisObj: Any, newValue: Any?)|change current this property valuev|
|<R> KProperty<R>.packageLevelGetPropertyValueByName(otherPropertyName: String): Any? |get other package level property value by other package level property name which is in the same kotlin file|
|<R> KFunction<R>.packageLevelGetPropertyValueByName(otherPropertyName: String): Any?|get other package level property value by other package level property name which is in the same kotlin file|
|<R> KProperty<R>.packageLevelChangePropertyValue(newValue: Any?)|change package level property value|
|<R> KProperty<R>.packageLevelChangeOtherPropertyValueByName(otherPropertyName: String, newValue: Any?)|change other package level property value by other package level property name which is in the same kotlin file|
|<R> KFunction<R>.packageLevelChangeOtherPropertyValueByName(otherPropertyName: String, newValue: Any?)|change other package level property value by other package level property name which is in the same kotlin file|
|<R> KProperty<R>.packageLevelInvokeMethodByName(methodName: String, vararg args: Any?): Any? |invoke package level method by name which is in the same kotlin file|
|<R> KFunction<R>.packageLevelInvokeMethodByName(methodName: String, vararg args: Any?): Any?|invoke package level method by name which is in the same kotlin file|
 
All method don't care what the property or method visibility it is 

## Demo
For example a Kotlin file like this:
```kotlin

val topName = "topSeal"
val topNameWu = "topSealWu"
private val topAge = 666

private fun gotIt() = true

fun funDoubleAge(age: Int): Int {
    return age * 2
}

class TestDemo {
    private val name = "seal"
    val age = 28

    private fun isMan(): Boolean {
        return true
    }
}
```
Then we could do these :
```kotlin
   
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
    fun packageLevelInvokeMethodByName() {
        val methodResult = ::topName.packageLevelInvokeMethodByName("gotIt") as Boolean
        methodResult.should.be.`true`
    }

```

To see more usage cases ,you can have a look at the AndroidTest case in project.

## Others
* Welcome to raise any issue.
* Welcome to push a pull request 


## Find me useful ? :heart:
* Support me by clicking the :star: button on the upper right of this page. :v:

