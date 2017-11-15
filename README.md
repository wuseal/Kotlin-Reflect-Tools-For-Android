[![Bintray](https://img.shields.io/bintray/v/wusealking/maven/wu.seal.android.kotlinreflecttools.svg)](https://bintray.com/wusealking/maven/wu.seal.jvm.kotlinreflecttools#)
[![GitHub stars](https://img.shields.io/github/stars/wuseal/Kotlin-Reflect-Tools-For-Android.svg?style=social&label=Stars&style=plastic)](https://github.com/wuseal/Kotlin-Reflect-Tools-For-JVM/stargazers)
[![license](https://img.shields.io/github/license/wuseal/Kotlin-Reflect-Tools-For-Android.svg)](https://github.com/wuseal/Kotlin-Reflect-Tools-For-JVM/blob/master/LICENSE)
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
       compile 'wu.seal:kotlin-reflect-tools-for-android:1.0.0'
    ```
    
## APIs

|Method         |Describe          |
|:------------- |:-------------| 
|changeTopPropertyValue | change the top level property value |
| changeTopPropertyValueByName | change the top leve property name by porpery name     |
| getTopPropertyValueByName | get the top level property value by property name     |
|changeClassPropertyValue| change the class inner property value      | 
| changeClassPropertyValueByName | change the class inner property name by property name     |
| getClassPropertyValueByName | get the class inner property value by property name   |
| invokeTopMethodByMethodName | invoke the top level method with method name     |
| invokeClassMethodByMethodName | invoke class obj inner method with method name     |

All method don't care what the property or method visibility it is 

## Demo
For example a Kotlin file like this:
```kotlin
val topName = "topSeal"
private val topAge = 666
private val topAgeName = "666"
private fun preTopAge(): Int {
    return funPropertyReduceAge(topAge)
}
private fun nextTopAge(): Int {
    return funPropertyPlusAge(topAge)
}

val funPropertyPlusAge: (Int) -> Int = { age -> age + 1 }

val funPropertyReduceAge: (Int) -> Int = { age -> age - 1 }

fun funDoubleAge(age: Int): Int {
    return age * 2
}

class TestDemo {
    private val name = "seal"
    val age = 28

    private fun isMan(): Boolean {
        return true
    }

    fun nextAge(): Int {
        return age + 1
    }
}
```
Then we could do these :
```kotlin
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
    fun invokeMethodByMethodName() {
        val demoObj = TestDemo()
        val expectedObjMethodValue = true
        val getMethodValue = invokeClassMethodByMethodName(demoObj, "isMan")

        assertEquals(expectedObjMethodValue, getMethodValue)

    }

```

To see more usage cases ,you can have a look at the test case in project.

## Others
* Welcome to raise any issue.
* Welcome to push a pull request 


## Find me useful ? :heart:
* Support me by clicking the :star: button on the upper right of this page. :v:

