package wu.seal.android.kotlinreflecttools

import kotlin.jvm.internal.CallableReference
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty

/**
 *  API extension model for easy use
 * Created by Seal.Wu on 2018/1/9.
 */

/**
 * get object property value by name
 */
fun Any.getPropertyValue(propertyName: String): Any? {
    return getClassPropertyValueByName(this, propertyName)
}

/**
 * change object property value by name
 */
fun Any.changePropertyValue(propertyName: String, newValue: Any?) {
    changeClassPropertyValueByName(this, propertyName, newValue)
}

/**
 * change object property value by name
 */
fun <R> Any.changePropertyValueByPropertyReference(kProperty: KProperty<R>, newValue: Any?) {
    changeClassPropertyValue(this, kProperty, newValue)
}

/**
 * invoke a method through object by method name
 *
 */
fun Any.invokeMethod(methodName: String, vararg args: Any?): Any? {
    return invokeClassMethodByMethodName(this, methodName, *args)
}

/**
 * change current this property value
 */
fun <R> KProperty<R>.changeValue(thisObj: Any, newValue: Any?) {
    changeClassPropertyValue(thisObj, this, newValue)
}

/**
 * get other package level property value by other package level property name which is in the same kotlin file
 */
fun <R> KProperty<R>.packageLevelGetPropertyValueByName(otherPropertyName: String): Any? {
    return getTopPropertyValueByName(this as CallableReference, otherPropertyName)
}

/**
 * get other package level property value by other package level property name which is in the same kotlin file
 */
fun <R> KFunction<R>.packageLevelGetPropertyValueByName(otherPropertyName: String): Any? {
    return getTopPropertyValueByName(this as CallableReference, otherPropertyName)
}

/**
 * change package level property value
 */
fun <R> KProperty<R>.packageLevelChangePropertyValue(newValue: Any?) {
    changeTopPropertyValue(this, newValue)
}

/**
 * change other package level property value by other package level property name which is in the same kotlin file
 */
fun <R> KProperty<R>.packageLevelChangeOtherPropertyValueByName(otherPropertyName: String, newValue: Any?) {
    changeTopPropertyValueByName(this as CallableReference, otherPropertyName, newValue)
}

/**
 * change other package level property value by other package level property name which is in the same kotlin file
 */
fun <R> KFunction<R>.packageLevelChangeOtherPropertyValueByName(otherPropertyName: String, newValue: Any?) {
    changeTopPropertyValueByName(this as CallableReference, otherPropertyName, newValue)
}


/**
 * invoke package level method by name which is in the same kotlin file
 */
fun <R> KProperty<R>.packageLevelInvokeMethodByName(methodName: String, vararg args: Any?): Any? {
    return invokeTopMethodByMethodName(this as CallableReference, methodName, *args)
}

/**
 * invoke package level method by name which is in the same kotlin file
 */
fun <R> KFunction<R>.packageLevelInvokeMethodByName(methodName: String, vararg args: Any?): Any? {
    return invokeTopMethodByMethodName(this as CallableReference, methodName, *args)
}


