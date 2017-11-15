package wu.seal.android.kotlinreflecttools


/**
Created By: Seal.Wu
Date: 2017/11/14
Time: 18:31
 */


val topName = "topSeal"
private val topAge = 666
private val topAgeName = "666"
private fun preTopAge(): Int {
    return funPropertyReduceAge(topAge)
}

private fun nextTopAge(): Int {
    return funPropertyPlusAge(topAge)
}


private fun doubleAgeAndPrint(age: Int):Int {
    val doubleAge = age*2
    println("double age = $doubleAge")
    return doubleAge
}

private fun plusTwoAge(age1: Int, age2: Int):Int {
    val plusAge = age1 + age2
    println("plus age = $plusAge")

    return plusAge
}


private fun plusNameAndAge(name: String, age2: Int):String {
    val plusNameAndAge = name + age2
    println("plusNameAndAge = $plusNameAndAge")

    return plusNameAndAge
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