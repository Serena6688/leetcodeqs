class Solution {
    public int[] finalPrices(int[] prices) {
        int n = prices.length;

        // 存最终结果
        int[] ans = new int[n];

        // 单调栈：存“候选折扣值”（右边的价格）
        Deque<Integer> stack = new ArrayDeque<Integer>();

        // 从右往左遍历（关键！！）
        for (int i = n - 1; i >= 0; i--) {

            // 维护一个“单调不增栈”（栈顶 <= 当前元素）
            // 把所有比当前价格大的元素弹掉
            while (!stack.isEmpty() && stack.peek() > prices[i]) {
                stack.pop();
            }

            // 如果栈空，说明右边没有 <= prices[i] 的数
            // 否则，栈顶就是“右边第一个 <= prices[i] 的数”
            ans[i] = stack.isEmpty() 
                     ? prices[i] 
                     : prices[i] - stack.peek();

            // 当前价格入栈，作为左边元素的候选折扣
            stack.push(prices[i]);
        }

        return ans;
    }
}