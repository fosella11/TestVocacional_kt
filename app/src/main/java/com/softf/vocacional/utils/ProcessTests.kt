package com.softf.vocacional.utils

import com.softf.vocacional.model.Question
import com.softf.vocacional.model.ResultTestOne

class ProcessTests {

    fun testOneGetInterest(arrayList: List<Question>): String {
        val a1 = intArrayOf(98, 12, 64, 53, 85, 1, 78, 20, 71, 91)
        val a2 = intArrayOf(9, 34, 80, 25, 95, 67, 41, 74, 56, 89)
        val a3 = intArrayOf(21, 45, 96, 57, 28, 11, 5, 3, 81, 36)
        val a4 = intArrayOf(33, 92, 70, 8, 87, 62, 23, 44, 16, 52)
        val a5 = intArrayOf(75, 6, 19, 38, 60, 27, 83, 54, 47, 97)
        val a6 = intArrayOf(84, 31, 48, 73, 5, 65, 14, 37, 58, 24)
        val a7 = intArrayOf(77, 42, 88, 17, 93, 32, 68, 49, 35, 61)
        val resultQustions = ArrayList<ResultTestOne>()

        var r1 = 0
        var r2 = 0
        var r3 = 0
        var r4 = 0
        var r5 = 0
        var r6 = 0
        var r7 = 0

        for (i in 1 until arrayList.size) {
            if (arrayList[i].response) {
                if (a1.contains(arrayList[i].questionId)) r1++
                if (a2.contains(arrayList[i].questionId)) r2++
                if (a3.contains(arrayList[i].questionId)) r3++
                if (a4.contains(arrayList[i].questionId)) r4++
                if (a5.contains(arrayList[i].questionId)) r5++
                if (a6.contains(arrayList[i].questionId)) r6++
                if (a7.contains(arrayList[i].questionId)) r7++
            }
        }
        resultQustions.add(ResultTestOne(1, r1, "I1"))
        resultQustions.add(ResultTestOne(2, r2, "I2"))
        resultQustions.add(ResultTestOne(3, r3, "I3"))
        resultQustions.add(ResultTestOne(4, r4, "I4"))
        resultQustions.add(ResultTestOne(5, r5, "I5"))
        resultQustions.add(ResultTestOne(6, r6, "I6"))
        resultQustions.add(ResultTestOne(7, r7, "I7"))
        var resultTestIs = "ERROR"
        if(!resultQustions.max()?.resp.isNullOrBlank()) resultTestIs = resultQustions.max()?.resp!!
        return resultTestIs
    }

    fun testOneGetAptitude(arrayList: List<Question>): String {
        val a1 = intArrayOf(15, 51, 2, 46)
        val a2 = intArrayOf(63, 30, 72, 86)
        val a3 = intArrayOf(22, 39, 76, 82)
        val a4 = intArrayOf(69, 40, 29, 4)
        val a5 = intArrayOf(26, 59, 90, 10)
        val a6 = intArrayOf(13, 66, 18, 43)
        val a7 = intArrayOf(94, 7, 79, 55)
        val resultQustions = ArrayList<ResultTestOne>()

        var r1 = 0
        var r2 = 0
        var r3 = 0
        var r4 = 0
        var r5 = 0
        var r6 = 0
        var r7 = 0

        for (i in 1 until arrayList.size) {
            if (arrayList[i].response) {
                if (a1.contains(arrayList[i].questionId)) r1++
                if (a2.contains(arrayList[i].questionId)) r2++
                if (a3.contains(arrayList[i].questionId)) r3++
                if (a4.contains(arrayList[i].questionId)) r4++
                if (a5.contains(arrayList[i].questionId)) r5++
                if (a6.contains(arrayList[i].questionId)) r6++
                if (a7.contains(arrayList[i].questionId)) r7++
            }
        }
        resultQustions.add(ResultTestOne(1, r1, "A1"))
        resultQustions.add(ResultTestOne(2, r2, "A2"))
        resultQustions.add(ResultTestOne(3, r3, "A3"))
        resultQustions.add(ResultTestOne(4, r4, "A4"))
        resultQustions.add(ResultTestOne(5, r5, "A5"))
        resultQustions.add(ResultTestOne(6, r6, "A6"))
        resultQustions.add(ResultTestOne(7, r7, "A7"))
        var resultTestIs = "ERROR"
        if(!resultQustions.max()?.resp.isNullOrBlank()) resultTestIs = resultQustions.max()?.resp!!

        return resultTestIs
    }
    //TEST TWO

    //Te da el mas alto y el siguiente
    fun testTwoGetAreas(arrayList: List<Question>): ArrayList<String> {
        val resultStrings = ArrayList<String>()
        val a1 = intArrayOf(4, 9, 12, 20, 28, 31, 35, 39, 43, 46, 50, 65, 67, 68, 75, 77)
        val a2 = intArrayOf(6, 13, 23, 25, 34, 37, 38, 42, 49, 52, 55, 63, 66, 70, 72, 78)
        val a3 = intArrayOf(5, 10, 15, 19, 21, 26, 29, 33, 36, 44, 53, 56, 59, 62, 71, 80)
        val a4 = intArrayOf(1, 7, 11, 17, 18, 24, 30, 41, 48, 51, 58, 60, 61, 64, 73, 79)
        val a5 = intArrayOf(2, 3, 8, 14, 16, 22, 27, 32, 40, 45, 47, 54, 57, 69, 74, 76)
        val resultQustions = ArrayList<ResultTestOne>()

        var r1 = 0
        var r2 = 0
        var r3 = 0
        var r4 = 0
        var r5 = 0

        for (i in 1 until arrayList.size) {
            if (arrayList[i].response) {
                if (a1.contains(arrayList[i].questionId)) r1++
                if (a2.contains(arrayList[i].questionId)) r2++
                if (a3.contains(arrayList[i].questionId)) r3++
                if (a4.contains(arrayList[i].questionId)) r4++
                if (a5.contains(arrayList[i].questionId)) r5++
            }
        }
        resultQustions.add(ResultTestOne(1, r1, "AA1"))
        resultQustions.add(ResultTestOne(2, r2, "AA2"))
        resultQustions.add(ResultTestOne(3, r3, "AA3"))
        resultQustions.add(ResultTestOne(4, r4, "AA4"))
        resultQustions.add(ResultTestOne(5, r5, "AA5"))
        var max = 0
        var idMax = 0
        var resultTestIs = "ERROR"
        for (j in 1 until resultQustions.size) {
            if (resultQustions[j].sum > max) {
                max = resultQustions[j].sum
                resultTestIs = resultQustions[j].resp
                idMax = j
            }
        }
        resultStrings.add(resultTestIs)
        resultQustions.removeAt(idMax)
        max = 0
        resultTestIs = "ERROR"
        for (j in 1 until resultQustions.size) {
            if (resultQustions[j].sum > max) {
                max = resultQustions[j].sum
                resultTestIs = resultQustions[j].resp
            }
        }
        resultStrings.add(resultTestIs)
        return resultStrings
    }

    companion object {
        private var instance: ProcessTests? = null


        fun getInstance(): ProcessTests {
            if (instance == null) {
                instance = ProcessTests()
            }
            return instance as ProcessTests
        }
    }
}