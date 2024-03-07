package com.lego.resource.datastructure

import spock.lang.Specification

class TrieTest extends Specification {

    private Trie trie

    def setup() {
        trie = new Trie()
        String[] nameArray = [
                "가나다",
                "가나다라",
                "가나다마",
                "가나다라마",
                "나나콘",
                "가나다",
                "가나다",
                "가나다라"
        ]

        for (int i = 0; i < nameArray.size(); i++) {
            trie.save(nameArray[i], i)
        }

    }

    def "findTest"() {
        expect:
        trie.find(name) == result

        where:
        name    | result
        "가나다"   | [0L, 5L, 6L]
        "가나다라"  | [1L, 7L]
        "가나다마"  | [2L]
        "가나다라마" | [3L]
        "나나콘"   | [4L]
        "나"     | []
        "마"     | []
        ""      | []
        null    | []
    }

    def "saveTest"() {
        given:
        trie.save("ABC", 102)
        trie.save("ABC", 103)
        trie.save("ABC", null)
        trie.save("", 101)
        trie.save(null, 100)

        expect:
        trie.find(name) == result

        where:
        name  | result
        "ABC" | [102L, 103L]
        "나나콘" | [4L]
        "가나다" | [0L, 5L, 6L]
        "나"   | []
        ""    | []
        null  | []
    }

    def "deleteTest"() {
        given:
        trie.delete("나나콘", 5L)
        trie.delete("가나다마", 2L)
        trie.delete("가나다", 5L)
        trie.delete("가나다", null)
        trie.delete("가나다라", 1L)
        trie.delete("나초", 1L)
        trie.delete("가나다라", 7L)
        trie.delete("가나다라", 8L)
        trie.delete("", null)
        trie.delete(null, null)

        expect:
        trie.find(name) == result

        where:
        name    | result
        "나나콘"   | [4L]
        "가나다마"  | []
        "가나다"   | [0L, 6L]
        "가나다라"  | []
        "가나다라마" | [3L]
        ""      | []
        null    | []
    }

}
