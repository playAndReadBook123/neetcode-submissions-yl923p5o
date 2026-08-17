class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        
        if(n == 1){
            return List.of(0);
        }
        int cnt = n;

        Map<Integer, List<Integer>> map = new HashMap<>();
        int[] degree = new int[n];

        for(int[] edg : edges){
            int v1 = edg[0];
            int v2 = edg[1];
            map.computeIfAbsent(v1, k -> new ArrayList<>()).add(v2);
            map.computeIfAbsent(v2, k -> new ArrayList<>()).add(v1);

            degree[v1]++;
            degree[v2]++;
        }

        Queue<Integer> que = new LinkedList<>();

        for(int i = 0; i < n; i++){
            if(degree[i] == 1){
                que.offer(i);
            }
        }

        while(cnt > 2){
            int size = que.size();
            while(size-- > 0){
                int cur = que.poll();
                cnt--;
                for(int next : map.getOrDefault(cur, new ArrayList<>())){
                    degree[next]--;
                    if(degree[next] == 1){
                        que.offer(next);
                    }
                }
            }
        }

        List<Integer> res = new ArrayList<>();
        while(!que.isEmpty()){
            res.add(que.poll());
        }

        return res;
    }
}