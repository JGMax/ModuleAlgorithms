import kotlin.random.Random

data class Data(var x: Int, var y: Int)

fun gcd(a: Int, b: Int, data: Data): Int {
    data.apply {
        if (a == 0) {
            x = 0
            y = 1
            return b
        }
        val data1 = Data(0, 0)
        val d = gcd(b % a, a, data1)
        x = data1.y - (b / a) * data1.x
        y = data1.x
        return d
    }
}

fun getPower(n1: Long): List<Long> {
    var n = n1
    var power = 0L
    while (n % 2 == 0L) {
        n /= 2
        power += 1
    }

    return listOf(power, n)
}

fun test1MillerRabin(a: Long, d: Long, n: Long): Boolean {
    return a.powMod(d, n) == 1L
}

fun test2MillerRabin(a: Long, n: Long, d: Long, s: Long): Boolean {
    for (r in 0 until s) {
        if (a.powMod(2L.pow(r) * d, n) == n - 1) {
            return true
        }
    }
    return false
}

fun Long.pow(n: Long) : Long {
    if (n == 1L) {
        return this
    }

    if (n == 0L) {
        return 1L
    }

    return if (n % 2 == 0L) {
        val value = this.pow(n / 2)
        value * value
    } else {
        this.pow(n - 1) * this
    }
}

fun testMillerRabin(n: Long, k: Long) {
    var test1Ok = 0
    var test2Ok = 0
    var fails = 0
    val (s, d) = getPower(n - 1)

    for (i in 0 until k) {
        val a = Random.nextLong(1L, n)
        if (test1MillerRabin(a, d, n)) {
            test1Ok += 1
        } else {
            if (test2MillerRabin(a, n, d, s)) {
                test2Ok += 1
            } else {
                fails += 1
            }
        }
    }

    if (fails == 0) {
        println("Miller-Rabin test: True $fails $test1Ok $test2Ok")
    } else {
        println("Miller-Rabin test: False $fails $test1Ok $test2Ok")
    }
}

fun Long.multiplyMod(_n: Long, mod: Long) : Long {
    var r = 0L
    var a = this
    var n = _n
    while (n != 0L) {
        if (n % 2 == 1L) {
            r = (r + a) % mod
        }
        a = (2 * a) % mod
        n /= 2
    }
    return r
}

fun Long.powMod(_n: Long, mod: Long) : Long {
    var r = 1L
    var n = _n
    var a = this
    while(n != 0L) {
        if (n % 2 == 1L) {
            r = r.multiplyMod(a, mod)
        }
        a = a.multiplyMod(a, mod)
        n /= 2
    }
    return r
}

fun testFermat(n: Long) {
    var fails = 0
    var ok = 0
    for (a in 1L until n) {
        if (a.powMod(n - 1, n) == 1L) {
            ok += 1
        } else {
            fails += 1
        }
    }
    if (fails == 0) {
        println("Fermat test: True $fails $ok")
    } else {
        println("Fermat test: False $fails $ok")
    }
}

fun main() {
    val a = readLine()?.toLong()
    if (a != null) {
        testMillerRabin(a, 25)
        //testFermat(a)
    }
}