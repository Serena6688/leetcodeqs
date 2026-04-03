class Solution {
    public int[] twoSum(int[] numbers, int target) {
        // 双指针：n 指向数组开头，m 指向数组末尾
        int n = 0, m = numbers.length - 1;

        // 当两个指针没有相遇时继续搜索
        while (n < m) {

            // 当前两个数的和
            int sum = numbers[n] + numbers[m];

            // 如果刚好等于 target，返回 1-based 下标
            if (sum == target) {
                return new int[]{n + 1, m + 1};
            } 
            // 如果和太小，说明需要更大的数 → 左指针右移
            else if (sum < target) {
                n++;
            } 
            // 如果和太大，说明需要更小的数 → 右指针左移
            else {
                m--;
            }
        }

        // 理论上一定有解，这里只是防御性返回
        return new int[0];
    }
}