class Solution {
    public boolean canReach(String s, int minJump, int maxJump) {
        
        Queue<Integer> que = new LinkedList<>();
        que.offer(0);

        if(s.charAt(s.length()-1) == '1'){
            return false;
        }

        int far = 0;

        while(!que.isEmpty()){
            int cur = que.poll();

            int start = Math.max(cur+minJump, far + 1);

            for(int j = start; j < Math.min(cur + maxJump+1, s.length()); j++){
                if(s.charAt(j) == '0'){
                    que.offer(j);
                    if(j == s.length()-1){
                        return true;
                    }
                }
            }

            far = cur + maxJump;
        }

        return false;
    }
}