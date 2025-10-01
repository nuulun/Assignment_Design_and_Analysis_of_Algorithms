import subprocess
import csv
import os
from datetime import datetime

# Папка для сохранения результатов
RESULTS_DIR = "results"
os.makedirs(RESULTS_DIR, exist_ok=True)

OUTPUT_FILE = os.path.join(RESULTS_DIR, f"bench_{datetime.now().strftime('%Y%m%d_%H%M%S')}.csv")

# Алгоритмы, которые будем гонять
ALGORITHMS = ["MergeSort", "QuickSort", "SelectMoM", "ClosestPair"]

# Количество повторов
TRIALS = 100

def run_java_benchmark(algo, n):
    """
    Запускаем java Benchmark c параметрами
    """
    cmd = ["java", "-cp", "target/classes", "algo.Benchmark", algo, str(n), str(TRIALS)]
    result = subprocess.run(cmd, capture_output=True, text=True)
    return result.stdout.strip()

def main():
    # Размеры входных данных (можешь настроить)
    input_sizes = [100, 500, 1000, 5000, 10000]

    with open(OUTPUT_FILE, "w", newline="") as f:
        writer = csv.writer(f)
        writer.writerow(["Algorithm", "N", "Trial", "Comparisons", "Allocations", "MaxDepth", "TimeMs"])

        for algo in ALGORITHMS:
            for n in input_sizes:
                print(f"▶ Running {algo} with n={n}")
                output = run_java_benchmark(algo, n)
                
                # Ожидаем, что Java печатает строки в формате CSV
                # Algo,n,trial,comparisons,allocations,maxDepth,timeMs
                for line in output.splitlines():
                    if line.startswith(algo):
                        writer.writerow(line.split(","))

    print(f"✅ Results saved in {OUTPUT_FILE}")


if __name__ == "__main__":
    main()
