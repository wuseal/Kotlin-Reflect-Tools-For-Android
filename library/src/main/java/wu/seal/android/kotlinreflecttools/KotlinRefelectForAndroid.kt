package wu.seal.android.kotlinreflecttools

import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.lang.reflect.Modifier
import java.util.*
import kotlin.jvm.internal.CallableReference
import kotlin.jvm.internal.PropertyReference
import kotlin.reflect.KProperty

/**
Created By: Seal.Wu
Date: 2017/11/8
Time: 14:24
 */

/**
 * change the property value with new value int the special KProperty in package level property ,not the property in class
 *
 */
fun <R> changeTopPropertyValue(property: KProperty<R>, newValue: R): Boolean =
        changePropertyValue(null, property, newValue)

/**
 * change the property value with new value int the special KProperty inside a  class level ,not the property not in any class
 */
fun <R> changeClassPropertyValue(classObj: Any, property: KProperty<R>, newValue: R): Boolean =
        changePropertyValue(classObj, property, newValue)

private fun <R> changePropertyValue(classObj: Any?, property: KProperty<R>, newValue: R): Boolean {
    val owner = (property as PropertyReference).owner
    val propertyName = property.name
    val containerClass: Class<*>
    try {
        containerClass = (owner!!::class.members as ArrayList).firstOrNull { it.name == "jClass" }?.call(owner) as Class<*>
    } catch (e: Exception) {
        throw IllegalArgumentException("No such property 'jClass'")
    }

    var tobeSearchFieldClass: Class<*>? = containerClass
    while (tobeSearchFieldClass != null) {

        tobeSearchFieldClass.declaredFields.forEach { field ->
            if (field.name == propertyName) {
                field.isAccessible = true
                field.set(classObj, newValue)
                return true
            }
        }

        tobeSearchFieldClass = tobeSearchFieldClass.superclass
    }
    return false
}

/**
 * change the property value with new value int the special property name inside a class level ,not the property not in any class
 */
fun <R> changeClassPropertyValueByName(classObj: Any, propertyName: String, newValue: R): Boolean {
    val containerClass: Class<*> = classObj::class.java

    var tobeSearchFieldClass: Class<*>? = containerClass
    while (tobeSearchFieldClass != null) {

        tobeSearchFieldClass.declaredFields.forEach { field ->
            if (field.name == propertyName) {
                field.isAccessible = true
                field.set(classObj, newValue)
                return true
            }
        }

        tobeSearchFieldClass = tobeSearchFieldClass.superclass
    }
    return false
}

/**
 * change the property value with new value int the special Property name in package level property ,not the property in class
 */
fun changeTopPropertyValueByName(otherCallableReference: CallableReference, propertyName: String, newValue: Any?) {

    val owner = otherCallableReference.owner
    val containerClass: Class<*>
    try {
        containerClass = (owner!!::class.members as ArrayList).firstOrNull { it.name == "jClass" }?.call(owner) as Class<*>
    } catch (e: Exception) {
        throw IllegalArgumentException("No such property 'jClass'")
    }
    var tobeSearchFieldClass: Class<*>? = containerClass

    while (tobeSearchFieldClass != null) {

        tobeSearchFieldClass.declaredFields.forEach { field ->
            if (field.name == propertyName) {
                field.isAccessible = true
                if (Modifier.isStatic(field.modifiers)) {
                    field.set(null, newValue)
                } else {
                    throw IllegalStateException("It is not a top property : $propertyName")
                }
                return
            }
        }
        tobeSearchFieldClass = tobeSearchFieldClass.superclass
    }
    throw IllegalArgumentException("Can't find the property named :$propertyName in the same file with ${otherCallableReference.name}")
}

/**
 * get the property value from a class object ,no matter whether the property is public ,private or intenel
 */
fun getClassPropertyValueByName(classObj: Any, propertyName: String): Any? {
    val containerClass: Class<*> = classObj::class.java

    var tobeSearchFieldClass: Class<*>? = containerClass

    while (tobeSearchFieldClass != null) {

        tobeSearchFieldClass.declaredFields.forEach { field ->
            if (field.name == propertyName) {
                field.isAccessible = true

                return field.get(classObj)
            }
        }
        tobeSearchFieldClass = tobeSearchFieldClass.superclass
    }

    return null
}

/**
 * get the property value in the top of a kotlin file(not in any kotlin class) ,no matter whether the property is public ,private or internal
 */
fun getTopPropertyValueByName(otherCallableReference: CallableReference, propertyName: String): Any? {

    val owner = otherCallableReference.owner
    val containerClass: Class<*>
    try {
        containerClass = (owner!!::class.members as ArrayList).firstOrNull { it.name == "jClass" }?.call(owner) as Class<*>
    } catch (e: Exception) {
        throw IllegalArgumentException("No such property 'jClass'")
    }

    var tobeSearchFieldClass: Class<*>? = containerClass

    while (tobeSearchFieldClass != null) {

        tobeSearchFieldClass.declaredFields.forEach { field ->
            if (field.name == propertyName) {
                field.isAccessible = true

                /**
                 * top property(package property) should be static in java level
                 * or throw an exception
                 * */
                if (Modifier.isStatic(field.modifiers)) {
                    return field.get(null)
                } else {
                    throw IllegalStateException("It is not a top property : $propertyName")
                }
            }
        }
        tobeSearchFieldClass = tobeSearchFieldClass.superclass
    }

    throw IllegalArgumentException("Can't find the property named :$propertyName in the same file with ${otherCallableReference.name}")
}

/**
 * invoke a method by name from a classObj,no matter whether the property is public ,private or internal
 */
fun invokeClassMethodByMethodName(classObj: Any, methodName: String, vararg methodArgs: Any?): Any? {
    val containerClass: Class<*> = classObj::class.java

    var tobeSearchMethodClass: Class<*>? = containerClass

    while (tobeSearchMethodClass != null) {

        tobeSearchMethodClass.declaredMethods.forEach { method ->
            if (method.name == methodName && method.parameterTypes.size == methodArgs.size) {
                method.isAccessible = true
                try {
                    if (methodArgs.isNotEmpty()) {

                        return method.invoke(classObj, *methodArgs)
                    } else {
                        return method.invoke(classObj)
                    }
                } catch (e: IllegalArgumentException) {
                    return@forEach
                }
            }
        }
        tobeSearchMethodClass = tobeSearchMethodClass.superclass
    }

    throw IllegalArgumentException("Can't find the method named :$methodName  with args ${methodArgs.toList().toString()} in the classObj : $classObj")
}

/**
 * invoke a method by name from a kotlin file(not in any kotlin class),no matter whether the property is public ,private or internal
 */
fun invokeTopMethodByMethodName(otherCallableReference: CallableReference, methodName: String, vararg methodArgs: Any?): Any? {
    val owner = otherCallableReference.owner
    val containerClass: Class<*>
    try {
        containerClass = (owner!!::class.members as ArrayList).firstOrNull { it.name == "jClass" }?.call(owner) as Class<*>
    } catch (e: Exception) {
        throw IllegalArgumentException("No such property 'jClass'")
    }

    var tobeSearchMethodClass: Class<*>? = containerClass

    while (tobeSearchMethodClass != null) {

        tobeSearchMethodClass.declaredMethods.forEach { method ->
            if (method.name == methodName && method.parameterTypes.size == methodArgs.size) {
                method.isAccessible = true

                try {
                    if (methodArgs.isNotEmpty()) {
                        return method.invoke(null, *methodArgs)
                    } else {
                        return method.invoke(null)
                    }
                } catch (e: IllegalArgumentException) {
                    return@forEach
                }
            }
        }
        tobeSearchMethodClass = tobeSearchMethodClass.superclass
    }
    throw IllegalArgumentException("Can't find the method named :$methodName with args ${methodArgs.toList().toString()} in the same file with ${otherCallableReference.name}")

}
