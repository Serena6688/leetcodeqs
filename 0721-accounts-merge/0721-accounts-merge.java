class Solution {

    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        // email -> [account index list]
        // 表示：一个邮箱在哪些账户里出现
        Map<String, List<Integer>> emailToIdx = new HashMap<>();

        // 遍历所有账户
        for (int i = 0; i < accounts.size(); i++) {

            // 从 k=1 开始，因为 k=0 是用户名，不是邮箱
            for (int k = 1; k < accounts.get(i).size(); k++) {

                String email = accounts.get(i).get(k);

                // 如果这个 email 不存在，就新建一个 list
                // 然后把当前账户 index i 加进去
                emailToIdx
                    .computeIfAbsent(email, x -> new ArrayList<>())
                    .add(i);
            }
        }

        // 最终答案
        List<List<String>> ans = new ArrayList<>();

        // 标记每个账户是否已经访问过（避免重复 DFS）
        boolean[] vis = new boolean[accounts.size()];

        // 用来收集当前“连通块”的所有邮箱
        Set<String> emailSet = new HashSet<>();

        // 遍历每个账户，尝试作为 DFS 起点
        for (int i = 0; i < accounts.size(); i++) {

            // 如果这个账户已经处理过，就跳过
            if (vis[i]) {
                continue;
            }

            // 清空上一次 DFS 收集的邮箱
            emailSet.clear();

            // 从当前账户开始 DFS，收集所有关联账户的邮箱
            dfs(i, accounts, emailToIdx, vis, emailSet);

            // 把收集到的邮箱转成 list
            List<String> res = new ArrayList<>(emailSet);

            // 按字典序排序
            Collections.sort(res);

            // 把用户名插到最前面
            // accounts.get(i).get(0) 就是 name
            res.add(0, accounts.get(i).get(0));

            // 加入最终答案
            ans.add(res);
        }

        return ans;
    }


    private void dfs(
        int i,                                   // 当前访问的账户 index
        List<List<String>> accounts,
        Map<String, List<Integer>> emailToIdx,
        boolean[] vis,
        Set<String> emailSet
    ) {

        // 标记当前账户已经访问
        vis[i] = true;

        // 遍历当前账户的所有邮箱
        for (int k = 1; k < accounts.get(i).size(); k++) {

            String email = accounts.get(i).get(k);

            // 如果这个邮箱已经处理过，跳过
            // 防止重复扩展
            if (emailSet.contains(email)) {
                continue;
            }

            // 把这个邮箱加入当前连通块
            emailSet.add(email);

            // 通过 email 找到所有“包含这个邮箱的账户”
            for (int j : emailToIdx.get(email)) {

                // 如果这些账户还没访问过
                if (!vis[j]) {

                    // 继续 DFS（扩展连通块）
                    dfs(j, accounts, emailToIdx, vis, emailSet);
                }
            }
        }
    }
}