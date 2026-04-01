class Solution {

    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();

        // 大根堆：堆顶是字典序最大的字符串
        PriorityQueue<String> words = new PriorityQueue<>((a, b) -> b.compareTo(a));
    }

    private void addWord(TrieNode root, String word) {
        TrieNode cur = root;

        for (char ch : word.toCharArray()) {
            cur.children.putIfAbsent(ch, new TrieNode());
            cur = cur.children.get(ch);

            cur.words.offer(word);
            if (cur.words.size() > 3) {
                cur.words.poll(); // 弹出字典序最大的，保留最小的3个
            }
        }
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        TrieNode root = new TrieNode();

        for (String word : products) {
            addWord(root, word);
        }

        List<List<String>> ans = new ArrayList<>();
        TrieNode cur = root;
        boolean dead = false;

        for (char ch : searchWord.toCharArray()) {
            if (dead || !cur.children.containsKey(ch)) {
                ans.add(new ArrayList<>());
                dead = true;
            } else {
                cur = cur.children.get(ch);

                // 注意：不能直接把原堆弹空，所以复制一份
                PriorityQueue<String> copy = new PriorityQueue<>(cur.words);
                List<String> list = new ArrayList<>();

                while (!copy.isEmpty()) {
                    list.add(copy.poll());
                }

                Collections.reverse(list); // 变成字典序升序
                ans.add(list);
            }
        }

        return ans;
    }
}