package com.example.kilvvr_cities_retrival_task.utils

import com.example.kilvvr_cities_retrival_task.models.City

/**
 * choosing the Trie data structure to index our list of cities will reduce the running time from
 * linear time of the size of the 200k entry list of cities to just the length of the prefix
 * which user will enter.
 *
 */
class Trie {

    private val root = TrieNode()
    /**
     * Inserts a city into the Trie.
     *
     * This method creates a path in the Trie for the city's name, character by character.
     * At the end of the path, it marks the last node as the end of a word and stores the city object.
     *
     * @param city The City object to be inserted into the Trie.
     * @time O(m), where m is the length of the city name.
     * @space O(m) in the worst case, if no common prefixes exist.
     */
    fun insert(city: City) {
        var curr = root
        for (char in city.name.lowercase()) {
            curr = curr.children.getOrPut(char) { TrieNode() }
        }
        curr.isEndOfWord = true
        curr.city = city
    }

    /**
     * Searches for cities in the Trie that start with the given prefix.
     *
     * This method traverses the Trie to find the node corresponding to the last character of the prefix,
     * then collects all cities in the subtree rooted at that node.
     * depends on other method following called collectCities()
     *
     * @param prefix The prefix to search for.
     * @return A list of City objects whose names start with the given prefix.
     *         Returns an empty list if no matches are found.
     * @time O(p + k), where p is the length of the prefix and k is the number of matching cities.
     * @space O(k) for the returned list.
     */
    fun search(prefix: String): List<City>{
        var curr = root
        for (char in prefix.lowercase()){
            val node = curr.children[char] ?:  return emptyList()
            curr = node
        }
        return collectCities(curr)
    }

    /**
     * Recursively collects all cities in the subtree rooted at the given node.
     *
     * This private helper method is used by the search function to gather all cities
     * that match a given prefix.
     *
     * @param node The TrieNode from which to start collecting cities.
     * @return A list of all City objects found in the subtree.
     * @time O(k), where k is the number of cities in the subtree.
     * @space O(k) for the returned list, O(h) for the call stack where h is the height of the subtree.
     */
    private fun collectCities(node: TrieNode): List<City> {
        val results = mutableListOf<City>()
        if (node.isEndOfWord) node.city?.let { results.add(it) }
        for (child in node.children.values){
            results.addAll(collectCities(child))
        }
        return results
    }
}