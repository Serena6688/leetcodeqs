import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public int maxEvents(int[][] events) {
        // 事件总数
        int n = events.length;

        // 找到所有会议中的最晚结束日期
        // 因为我们会按“天”从 1 遍历到 maxDay
        int maxDay = 0;
        for (int[] event : events) {
            maxDay = Math.max(maxDay, event[1]);
        }

        // 小根堆：存“当前这一天可以考虑参加的所有会议”的结束时间
        // 为什么存结束时间？
        // 因为贪心策略是：每天优先参加“最早结束”的会议
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // 先按开始时间升序排序
        // 这样我们就能随着日期 i 的推进，把所有 start <= i 的会议加入堆里
        Arrays.sort(events, (a, b) -> a[0] - b[0]);

        // 最终答案：最多参加多少个会议
        int ans = 0;

        // i 表示当前日期，从第 1 天遍历到最后一天
        // j 表示当前处理到 events 数组中的第几个会议
        for (int i = 1, j = 0; i <= maxDay; i++) {

            // 把所有“已经开始了”的会议加入堆中
            // 即开始时间 <= 当前天 i 的会议，都有资格在今天被选择
            while (j < n && events[j][0] <= i) {
                // 往堆里放这个会议的结束时间
                pq.offer(events[j][1]);
                j++;
            }

            // 把所有“已经过期”的会议从堆里删掉
            // 如果某个会议的结束时间 < 当前天 i，
            // 说明它昨天就结束了，今天已经不可能参加
            while (!pq.isEmpty() && pq.peek() < i) {
                pq.poll();
            }

            // 如果今天还有可参加的会议
            if (!pq.isEmpty()) {
                // 贪心地参加“结束时间最早”的那个会议
                // 因为它最紧急，越早参加越不容易浪费机会
                pq.poll();
                ans++;
            }
        }

        // 返回最多参加的会议数
        return ans;
    }
}