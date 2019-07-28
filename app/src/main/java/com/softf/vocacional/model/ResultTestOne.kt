package com.softf.vocacional.model

data class ResultTestOne(var id: Int, var sum: Int, var resp: String): Comparable<ResultTestOne>{
    override fun compareTo(other: ResultTestOne) = (sum - other.sum)
}