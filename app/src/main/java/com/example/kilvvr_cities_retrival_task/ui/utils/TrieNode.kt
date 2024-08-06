package com.example.kilvvr_cities_retrival_task.ui.utils

import com.example.kilvvr_cities_retrival_task.models.City

class TrieNode {
    var children = mutableMapOf<Char,TrieNode>()
    var isEndOfWord: Boolean = false
    var city: City? = null
}