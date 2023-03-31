package ru.potatophobe.linearsort

fun bucketSort(array: IntArray) {
    val arraySize = array.size
    val arrayMax = array.max()

    val buckets = Array<MutableList<Int>>(arraySize) { mutableListOf() }

    for (element in array) {
        val bucketIndex = (element * arraySize) / (arrayMax + 1)
        val bucket = buckets[bucketIndex]

        if (bucket.isEmpty()) {
            bucket.add(element)
        } else {
            var inserted = false

            for (i in bucket.indices) {
                if (bucket[i] > element) {
                    bucket.add(i, element)
                    inserted = true
                    break
                } else if (bucket[i] == element) {
                    bucket.add(i + 1, element)
                    inserted = true
                    break
                }
            }

            if (!inserted) {
                bucket.add(element)
            }
        }
    }

    var index = 0
    for (bucket in buckets) {
        for (element in bucket) {
            array[index++] = element
        }
    }
}

fun countingSort(array: IntArray) {
    val countArray = IntArray(array.max()) { 0 }

    for (element in array) {
        countArray[element - 1]++
    }

    for (i in 1..countArray.lastIndex) {
        countArray[i] += countArray[i - 1]
    }

    val arrayCopy = array.copyOf()

    var k = array.lastIndex
    while (k >= 0) {
        val countArrayIndex = arrayCopy[k] - 1
        array[countArray[countArrayIndex] - 1] = arrayCopy[k]
        countArray[countArrayIndex]--
        k--
    }
}

fun radixSort(array: IntArray) {
    val arrayMax = array.max()

    var place = 1
    while (arrayMax / place > 0) {
        val countArray = IntArray(10)

        for (i in array.indices) {
            val digit = (array[i] / place) % 10
            countArray[digit]++
        }

        for (i in 1..countArray.lastIndex) {
            countArray[i] += countArray[i - 1]
        }

        val arrayCopy = array.copyOf()

        for (i in arrayCopy.indices.reversed()) {
            val digit = (arrayCopy[i] / place) % 10
            array[countArray[digit] - 1] = arrayCopy[i]
            countArray[digit]--
        }
        place *= 10
    }
}
