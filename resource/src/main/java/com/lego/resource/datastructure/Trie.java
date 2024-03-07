package com.lego.resource.datastructure;

import io.micrometer.common.util.StringUtils;

import java.util.*;

public class Trie {
  private final TrieNode root;

  public Trie() {
    root = new TrieNode();
  }

  public List<Long> find(String word) {
    if (StringUtils.isEmpty(word)) {
      return Collections.emptyList();
    }

    TrieNode current = root;
    for (char ch : word.toCharArray()) {
      if (!current.children.containsKey(ch)) {
        return Collections.emptyList();
      }
      current = current.children.get(ch);
    }

    if (!current.isEndOfWord) {
      return Collections.emptyList();
    }

    return current.pkIdList;
  }

  public void save(String word, Long pkId) {
    if (StringUtils.isEmpty(word) || pkId == null) {
      return;
    }

    TrieNode current = root;
    for (char ch : word.toCharArray()) {
      current.children.putIfAbsent(ch, new TrieNode());
      current = current.children.get(ch);
    }

    current.isEndOfWord = true;
    current.pkIdList.add(pkId);
  }

  public void delete(String word, Long pkId) {
    if (StringUtils.isEmpty(word) || pkId == null) {
      return;
    }

    delete(root, word, 0, pkId);
  }

  private boolean delete(TrieNode current, String word, int index, Long pkId) {
    if (index == word.length()) {
      // 현재 노드가 단어의 끝이면서 자식이 없는 경우 삭제
      if (!current.isEndOfWord) {
        return false;
      }

      // 현재 노드 pkIdList 에서 pkId가 존재하는 경우 삭제
      if (!current.pkIdList.contains(pkId)) {
        return false;
      }

      current.pkIdList.removeIf(id -> id.equals(pkId));
      // 현재 노드 pkIdList 가 비었을 경우 삭제
      if (!current.pkIdList.isEmpty()) {
        return false;
      }

      current.isEndOfWord = false;
      return current.children.isEmpty();
    }

    char ch = word.charAt(index);
    TrieNode node = current.children.get(ch);
    if (node == null) {
      return false;
    }

    boolean shouldDeleteCurrentNode = delete(node, word, index + 1, pkId);

    // 자식 노드를 삭제한 경우 해당 자식 노드를 맵에서 삭제
    if (shouldDeleteCurrentNode) {
      current.children.remove(ch);
      // 현재 노드가 단어의 끝이면서 자식이 없고, pkIdList가 비었을 경우 삭제
      return current.isEndOfWord && current.children.isEmpty() && current.pkIdList.isEmpty();
    }

    return false;
  }


  static class TrieNode {
    Map<Character, TrieNode> children;
    boolean isEndOfWord;
    List<Long> pkIdList;

    public TrieNode() {
      children = new HashMap<>();
      isEndOfWord = false;
      pkIdList = new ArrayList<>();
    }
  }

}

