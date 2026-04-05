class Solution {
    public int largestSubmatrix(int[][] matrix) {
        // m = 行数, n = 列数
        int m = matrix.length;
        int n = matrix[0].length;

        // 记录目前找到的最大子矩阵面积
        int maxArea = 0;
        
        // =========================
        // 第一步：把原矩阵改造成“高度矩阵”
        // matrix[i][j] 表示：
        // 以 (i, j) 这个位置为底，往上连续有多少个 1
        // =========================
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 如果当前位置是 1
                if (matrix[i][j] == 1) {
                    // 那么它的高度 = 上一行同一列的高度 + 1
                    // 也就是把当前这个 1 接到上面连续的 1 下面
                    matrix[i][j] += matrix[i - 1][j];
                }
                // 如果当前位置本来是 0，就不用管
                // 高度仍然是 0，因为这里不能作为全 1 矩形的底
            }
        }
        
        // =========================
        // 第二步：逐行计算答案
        // 每一行现在都表示“这一行各列作为底时的高度”
        // 因为题目允许你重新排列列，所以我们把每一行排序
        // 把高的柱子放前面，这样更容易形成大面积矩形
        // =========================
        for (int i = 0; i < m; i++) {
            // 默认 Arrays.sort 是升序
            Arrays.sort(matrix[i]);

            // 反转后变成降序
            // 这样 matrix[i][0] >= matrix[i][1] >= ...
            reverse(matrix[i]);

            // 枚举这一行里“前 j+1 列”作为一个矩形的宽度
            for (int j = 0; j < n; j++) {
                // 宽度 = j + 1
                // 高度 = matrix[i][j]
                //
                // 为什么高度取 matrix[i][j]？
                // 因为已经按降序排好了：
                // 前 j+1 个数里，最小的那个就是 matrix[i][j]
                // 所以前 j+1 列能共同形成的矩形高度就是它
                //
                // 面积 = 宽 * 高
                maxArea = Math.max(maxArea, (j + 1) * matrix[i][j]);
            }
        }
        
        // 返回最大面积
        return maxArea;
    }
    
    private void reverse(int[] arr) {
        // 双指针反转数组
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            // 交换左边和右边
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            // 指针向中间移动
            left++;
            right--;
        }
    }
}