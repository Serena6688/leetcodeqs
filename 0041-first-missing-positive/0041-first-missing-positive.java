class Solution {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;

        // 第一轮：清理无效值
        // 把 <=0 的数全部变成 n+1（因为这些数对答案没有影响）
        for (int i = 0; i < n; ++i) {
            if (nums[i] <= 0) {
                nums[i] = n + 1;
            }
        }

        // 第二轮：用“负号”标记哪些数出现过
        for (int i = 0; i < n; ++i) {
            int num = Math.abs(nums[i]); // 注意：可能已经被标记成负数了，所以取绝对值

            // 只处理在 [1, n] 范围内的数
            if (num <= n) {
                // 把对应位置变成负数，表示 num 出现过
                // 例如：num = 3 → 标记 index=2
                nums[num - 1] = -Math.abs(nums[num - 1]);
            }
        }

        // 第三轮：找第一个“没被标记”的位置（正数）
        for (int i = 0; i < n; ++i) {
            if (nums[i] > 0) {
                // i位置是正数，说明 i+1 没出现过
                return i + 1;
            }
        }

        // 如果 1~n 都出现了
        return n + 1;
    }
}