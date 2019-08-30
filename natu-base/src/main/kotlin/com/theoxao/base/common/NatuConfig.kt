package com.theoxao.base.common

class NatuConfig {

    lateinit var ignore: List<String>

    lateinit var trigger: Map<String, String>

    lateinit var bean: List<String>

    lateinit var except: List<Except>
}

class Except {
    lateinit var name: String
    lateinit var trigger: String
}