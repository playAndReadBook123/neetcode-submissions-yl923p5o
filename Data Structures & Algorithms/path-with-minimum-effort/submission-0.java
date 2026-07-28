class Solution {

    int rows = 0;
    int cols = 0;
    public int minimumEffortPath(int[][] heights) {
        rows = heights.length;
        cols = heights[0].length;

        int left = 0;
        int right = Integer.MAX_VALUE;

        int res = 0;
        while(left <= right){
            int mid = left + (right - left) / 2;

            if(canReach(0, 0, mid, heights, new boolean[rows][cols])){
                res = mid;
                right = mid - 1;
            }else{
                left  = mid + 1;
            }
        }

        return res;
    }

    int[][] dirs = new int[][]{{1,0}, {-1,0}, {0, 1}, {0,-1}};

    boolean canReach(int r, int c, int val, int[][] heights, boolean[][] visited){

        if(r == rows-1 && c == cols-1){
            return true;
        }

        visited[r][c] = true;

        for(int[] dir : dirs){
            int nr = r + dir[0];
            int nc = c + dir[1];

            if(nr < 0 || nc < 0 || nr >= rows || nc >= cols || visited[nr][nc]){
                continue;
            }

            if(Math.abs(heights[nr][nc] - heights[r][c]) <= val){
                if(canReach(nr, nc, val, heights, visited)){
                    return true;
                }
            }
        }

        return false;
    }
}