package io.github.anderscheow.library.kotlin

/** Check not null */
fun <T1: Any, T2: Any, R: Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun <T1: Any, T2: Any, T3: Any, R: Any> safeLet(p1: T1?, p2: T2?, p3: T3?, block: (T1, T2, T3) -> R?): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}

fun <T1: Any, T2: Any, T3: Any, T4: Any, R: Any> safeLet(p1: T1?, p2: T2?, p3: T3?, p4: T4?, block: (T1, T2, T3, T4) -> R?): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null) block(p1, p2, p3, p4) else null
}

fun <T1: Any, T2: Any, T3: Any, T4: Any, T5: Any, R: Any> safeLet(p1: T1?, p2: T2?, p3: T3?, p4: T4?, p5: T5?, block: (T1, T2, T3, T4, T5) -> R?): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null) block(p1, p2, p3, p4, p5) else null
}

/** Check is String and is not null and not empty */
fun <T: Any, R: Any> isNotNullAndNotEmpty(p1: T?, block: (T) -> R?): R? {
    if (p1 is String? && !p1.isNullOrEmpty()) {
        p1?.let { block(it) }
    }

    return null
}

fun <T: Any, R: Any> isNotNullAndNotEmpty(p1: T?, p2: T?, block: (T, T) -> R?): R? {
    if (p1 is String && p2 is String) {
        if (!p1.isNotNullAndNotEmpty() && !p2.isNotNullAndNotEmpty()) {
            block(p1, p2)
        }
    }

    return null
}

fun <T: Any, R: Any> isNotNullAndNotEmpty(p1: T?, p2: T?, p3: T?, block: (T, T, T) -> R?): R? {
    if (p1 is String && p2 is String && p3 is String) {
        if (!p1.isNotNullAndNotEmpty() && !p2.isNotNullAndNotEmpty() && !p3.isNotNullAndNotEmpty()) {
            block(p1, p2, p3)
        }
    }

    return null
}

fun <T: Any, R: Any> isNotNullAndNotEmpty(p1: T?, p2: T?, p3: T?, p4: T?, block: (T, T, T, T) -> R?): R? {
    if (p1 is String && p2 is String && p3 is String && p4 is String) {
        if (!p1.isNotNullAndNotEmpty() && !p2.isNotNullAndNotEmpty() && !p3.isNotNullAndNotEmpty() && !p4.isNotNullAndNotEmpty()) {
            block(p1, p2, p3, p4)
        }
    }

    return null
}

fun <T: Any, R: Any> isNotNullAndNotEmpty(p1: T?, p2: T?, p3: T?, p4: T?, p5: T?, block: (T, T, T, T, T) -> R?): R? {
    if (p1 is String && p2 is String && p3 is String && p4 is String && p5 is String) {
        if (!p1.isNotNullAndNotEmpty() && !p2.isNotNullAndNotEmpty() && !p3.isNotNullAndNotEmpty() && !p4.isNotNullAndNotEmpty() && !p5.isNotNullAndNotEmpty()) {
            block(p1, p2, p3, p4, p5)
        }
    }

    return null
}

/** Extension for String to check is not null and not empty */
fun String?.isNotNullAndNotEmpty(): Boolean {
    return this != null && this.isNotEmpty()
}