package wu.seal.android.kotlinreflecttools


/**
Created By: Seal.Wu
Date: 2018/3/29
Time: 10:37
 */
open class Super {
    private val superFieldOne: String = "superFieldOne"
    private val superFieldTwo: Int = 2


    private fun superPrivateFun(returnValue:Boolean):Boolean {
        return returnValue
    }

    open fun getSignString():String {
        return "Super"
    }

}

class SubClass : Super() {
    private val subFieldOne: String = "subFieldOne"
    private val subFieldTwo: Int = 20

    private fun subPrivateFun(returnValue:Boolean):Boolean {
        return returnValue
    }
    override fun getSignString():String {
        return "Sub"
    }
}