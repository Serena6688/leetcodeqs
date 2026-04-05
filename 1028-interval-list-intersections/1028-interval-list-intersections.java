import java.util.*;

class Solution {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> res = new ArrayList<>();

        int i = 0, j = 0;

        while (i < firstList.length && j < secondList.length) {
            int startA = firstList[i][0];
            int endA = firstList[i][1];
            int startB = secondList[j][0];
            int endB = secondList[j][1];

            // 计算两个区间的交集范围
            int left = Math.max(startA, startB);
            int right = Math.min(endA, endB);

            // 如果 left <= right，说明有交集
            if (left <= right) {
                res.add(new int[]{left, right});
            }

            // 谁先结束，就移动谁
            if (endA < endB) {
                i++;
            } else {
                j++;
            }
        }

        return res.toArray(new int[res.size()][]);
    }
}