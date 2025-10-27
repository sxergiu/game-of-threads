🧬 Cellular Life Simulation (Multithreaded Java)

This repository contains a simple, multithreaded simulation of cellular life, demonstrating core Java concurrency concepts, including threads, atomic variables, and explicit/intrinsic locking mechanisms. The simulation tracks two types of cells (Asexual and Sexual) competing for a finite food resource.

🔬 Project Overview

The simulation models a basic ecosystem where individual cells (running as separate threads) must find food to survive and reproduce. The ResourceManager is the central, thread-safe manager for the shared environment.

Key Concepts

1. Concurrency: Each Cell is a Thread running its own life cycle concurrently.
2. Resource Contention: Cells compete to consume food from a shared pool managed by ResourceManager.
3. Synchronization: Uses synchronized blocks, Object.wait(), Object.notifyAll(), AtomicInteger, and ReentrantLock to prevent race conditions and deadlocks.
4. Deadlock Prevention: The SexualCell implements lock ordering based on cell ID to safely coordinate mating.
5. Thread Safety

⚙️ How to Run the Simulation

Clone the repository:
git clone [Your Repo URL]
cd [Your Repo Name]

Compile the Java files
javac com/threads/sim/GameOfLife.java

Run the main class:
java com.threads.sim.GameOfLife