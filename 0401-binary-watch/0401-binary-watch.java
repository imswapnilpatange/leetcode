// Time Complexity --> O(12*60) -> O(720) -> O(1)
// Space Complexity --> O(output size)

class Solution {
    public List<String> readBinaryWatch(int turnedOn) {
        List<String> result = new ArrayList<>();

        // Iterate through all possible hours (0-11)
        for(int hour = 0; hour < 12; hour++){

            // Iterate through all possible minutes (0-59)
            for(int minutes = 0; minutes < 60; minutes++){
                int totalBits = Integer.bitCount(hour) + Integer.bitCount(minutes);
                
                if(totalBits == turnedOn){
                    // Format minute with leading zero if needed
                    String time = hour + ":" + (minutes < 10 ? "0" + minutes : minutes);
                    result.add(time);
                }
            }
        }
        return result;
    }
}