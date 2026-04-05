class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        // email -> 唯一编号
        // 因为并查集通常处理的是整数编号，不直接处理字符串
        Map<String, Integer> emailToIndex = new HashMap<String, Integer>();

        // email -> name
        // 记录每个邮箱对应的用户名，最后组装答案时要用
        Map<String, String> emailToName = new HashMap<String, String>();

        // 当前已经给多少个邮箱分配过编号
        int emailsCount = 0;

        // 第一步：给每个邮箱分配一个唯一编号
        for (List<String> account : accounts) {
            // 当前账户的名字，account[0]
            String name = account.get(0);

            int size = account.size();

            // 从 1 开始遍历，因为 0 是名字，不是邮箱
            for (int i = 1; i < size; i++) {
                String email = account.get(i);

                // 如果这个邮箱还没出现过
                if (!emailToIndex.containsKey(email)) {
                    // 给它分配一个新的编号
                    emailToIndex.put(email, emailsCount++);

                    // 记录这个邮箱属于谁
                    emailToName.put(email, name);
                }
            }
        }

        // 创建并查集，大小 = 不同邮箱的数量
        UnionFind uf = new UnionFind(emailsCount);

        // 第二步：把同一个账户里的所有邮箱 union 到一起
        for (List<String> account : accounts) {
            // 取这个账户的第一个邮箱，作为“代表邮箱”
            String firstEmail = account.get(1);

            // 拿到第一个邮箱对应的编号
            int firstIndex = emailToIndex.get(firstEmail);

            int size = account.size();

            // 从第二个邮箱开始，把它们都和第一个邮箱合并
            for (int i = 2; i < size; i++) {
                String nextEmail = account.get(i);

                // 当前邮箱的编号
                int nextIndex = emailToIndex.get(nextEmail);

                // 合并这两个邮箱所在的集合
                uf.union(firstIndex, nextIndex);
            }
        }

        // index(root) -> 这个连通块里的所有邮箱
        Map<Integer, List<String>> indexToEmails = new HashMap<Integer, List<String>>();

        // 第三步：把所有邮箱按照“根节点”分组
        for (String email : emailToIndex.keySet()) {
            // 找到当前邮箱所属集合的根
            int index = uf.find(emailToIndex.get(email));

            // 拿到这个根节点对应的邮箱列表，如果没有就新建一个
            List<String> account = indexToEmails.getOrDefault(index, new ArrayList<String>());

            // 把当前邮箱加入该组
            account.add(email);

            // 放回 map
            indexToEmails.put(index, account);
        }

        // 最终结果
        List<List<String>> merged = new ArrayList<List<String>>();

        // 第四步：遍历每个分组，构造答案
        for (List<String> emails : indexToEmails.values()) {
            // 题目要求邮箱按字典序排序
            Collections.sort(emails);

            // 这个组里随便取一个邮箱，用它查用户名
            // 因为同一组一定属于同一个人
            String name = emailToName.get(emails.get(0));

            // 构造结果：[name, email1, email2, ...]
            List<String> account = new ArrayList<String>();
            account.add(name);
            account.addAll(emails);

            // 加入最终答案
            merged.add(account);
        }

        return merged;
    }
}

class UnionFind {
    int[] parent;

    public UnionFind(int n) {
        // parent[i] = i 表示一开始每个节点的父节点是自己
        // 也就是每个邮箱自己单独成一个集合
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public void union(int index1, int index2) {
        // 把 index2 所在集合的根，挂到 index1 所在集合的根下面
        // 也就是把两个集合合并
        parent[find(index2)] = find(index1);
    }

    public int find(int index) {
        // 如果当前节点的父节点不是自己
        // 说明它不是根节点，就继续往上找根
        if (parent[index] != index) {
            // 路径压缩：
            // 在找根的过程中，顺便把当前节点直接连到根上
            // 这样以后查找更快
            parent[index] = find(parent[index]);
        }

        // 返回根节点
        return parent[index];
    }
}