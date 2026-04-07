class Robot {
    int width, height;
    int x, y;
    int dir; // 0=East, 1=North, 2=West, 3=South
    int perimeter;
    boolean moved;

    public Robot(int width, int height) {
        this.width = width;
        this.height = height;
        this.x = 0;
        this.y = 0;
        this.dir = 0;
        this.perimeter = 2 * (width + height) - 4;
        this.moved = false;
    }
    
    public void step(int num) {
        moved = true;
        num %= perimeter;
        
        while (num > 0) {
            if (dir == 0) {
                int steps = Math.min(num, width - 1 - x);
                x += steps;
                num -= steps;
                if (num > 0) dir = 1;
            } else if (dir == 1) {
                int steps = Math.min(num, height - 1 - y);
                y += steps;
                num -= steps;
                if (num > 0) dir = 2;
            } else if (dir == 2) {
                int steps = Math.min(num, x);
                x -= steps;
                num -= steps;
                if (num > 0) dir = 3;
            } else {
                int steps = Math.min(num, y);
                y -= steps;
                num -= steps;
                if (num > 0) dir = 0;
            }
        }

        if (x == 0 && y == 0 && moved && num == 0) {
            dir = 3;
        }
    }
    
    public int[] getPos() {
        return new int[]{x, y};
    }
    
    public String getDir() {
        if (!moved) return "East";
        if (dir == 0) return "East";
        if (dir == 1) return "North";
        if (dir == 2) return "West";
        return "South";
    }
}