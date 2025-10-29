# 🧬 Cellular Life Simulation (Multithreaded Java)

This repository contains a **multithreaded simulation of cellular life**, demonstrating key Java concurrency concepts such as **threads, atomic variables, synchronization, and locking mechanisms**.  
The simulation models two types of cells — **Asexual** and **Sexual** — competing for a limited food resource in a shared environment.

---

## 🔬 Project Overview

The simulation represents a simple **ecosystem** where each cell runs as a **separate thread** with its own life cycle.  
Cells must **find food** to survive and **reproduce**, while the shared environment is managed by a central, thread-safe **ResourceManager**.

---

## 🧠 Key Concepts

1. **Concurrency**  
   Each `Cell` is a separate thread that executes independently, simulating real-time behavior.

2. **Resource Contention**  
   All cells compete for limited food units managed by `ResourceManager`.

3. **Synchronization**  
   The system uses:
   - `synchronized` blocks  
   - `Object.wait()` / `Object.notifyAll()`  
   - `AtomicInteger`  
   - `ReentrantLock`  
   to ensure proper coordination and prevent race conditions.

4. **Deadlock Prevention**  
   `SexualCell` instances implement **lock ordering** based on cell ID to coordinate mating safely.

5. **Thread Safety**  
   The `ResourceManager` provides a safe interface for all concurrent cell interactions with shared resources.

---

## ⚙️ How to Run the Simulation

### 1. Clone the Repository
```bash
git clone https://github.com/sxergiu/game-of-threads.git
cd [Your Repo Name]
```

### 2. Compile the Java Files
javac com/threads/sim/GameOfLife.java

### 3. Run the Main Class
java com.threads.sim.GameOfLife

## Architecture Diagram

```mermaid

flowchart LR

%% Core components
G["GameOfLife entrypoint"]
AC["AsexualCell\nThread"]
SC["SexualCell\nThread"]
RM["ResourceManager"]

G -->|"seed and start threads"| AC
G --> SC
G -->|"periodic stats"| RM

%% Abstract base
subgraph Model
  direction TB
  C["Cell (abstract thread)\nrun(): eat -> maybeReproduce -> sleep/die"]
  C --> AC
  C --> SC
end

%% Shared state & sync
subgraph Shared_State_ResourceManager
  direction TB
  FP["foodPool: AtomicInteger"]
  LC["livingCells: CopyOnWriteArrayList of Cell"]
  RS["readySexuals: queue/list"]
  ST["stats: AtomicInteger(s)"]
  RL["reproductionLock: ReentrantLock"]
end

RM --- FP
RM --- LC
RM --- RS
RM --- ST
RM --- RL

%% Interactions
C -->|"consumeFood(), register/remove, reportStats"| RM
AC -->|"requestAsexualReproduction()\n-> spawn 2 hungry children"| RM
SC -->|"requestSexualReadiness(), pairIfPossible()\n-> spawn 1 hungry child"| RM

%% Callouts
N1["After 10 meals -> reproduce"]
N2["Thread-safety via Atomics\nCOWAL, volatile flags\nReentrantLock"]
C -.-> N1
RM -.-> N2

```
