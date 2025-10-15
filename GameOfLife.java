import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ResourceManager {
    private AtomicInteger foodCount;
    private final Lock foodLock = new ReentrantLock();
    private final List<Cell> cells = new CopyOnWriteArrayList<>(); 

    public ResourceManager(int initialFoodCount) {
        this.foodCount = new AtomicInteger(initialFoodCount);
    }

    public boolean consumeFood() {
      // Logic here needs a lock despite using AtomicInteger
      // because there are 2 atomic operations in a sequence which doesn't guarantee thread safety
        foodLock.lock();
        try {
            int current = foodCount.get();
            if (current > 0) {
                foodCount.decrementAndGet();
                return true;
            }
            return false;
        } finally {
            foodLock.unlock();
        }
    }

    public void addFood(int amount) {
        foodCount.addAndGet(amount);
    }

    public int getFoodCount() {
        return foodCount.get();
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public void removeCell(Cell cell) {
        cells.remove(cell);
    }
}

abstract class Cell extends Thread {
    protected final int id;
    protected final int T_full;
    protected final int T_starve;
    protected int timesEaten;
    protected boolean hungry;
    protected boolean alive;
    protected ResourceManager resourceManager;

    public Cell(int id, ResourceManager resourceManager, int T_full, int T_starve) {
        this.id = id;
        this.resourceManager = resourceManager;
        this.T_full = T_full;
        this.T_starve = T_starve;
    }
}

class AsexualCell extends Cell {
    public AsexualCell(int id, ResourceManager resourceManager, int T_full, int T_starve) {
        super(id, resourceManager, T_full, T_starve);
    }
}

class SexualCell extends Cell {
    public SexualCell(int id, ResourceManager resourceManager, int T_full, int T_starve) {
        super(id, resourceManager, T_full, T_starve);
    }
}

public class GameOfLife {
    public static void main(String[] args) {
        System.out.println("Game of Life");
    }
}
