import pandas as pd
import matplotlib.pyplot as plt
import sys
import os

def main(csv_file, out_dir):
    os.makedirs(out_dir, exist_ok=True)

    df = pd.read_csv(csv_file)

    # Для каждого алгоритма построим график TimeMs vs N
    for algo in df["Algorithm"].unique():
        subset = df[df["Algorithm"] == algo]
        grouped = subset.groupby("N").mean(numeric_only=True)

        plt.figure()
        plt.plot(grouped.index, grouped["TimeMs"], marker="o")
        plt.title(f"{algo} - Runtime")
        plt.xlabel("Input size (N)")
        plt.ylabel("Time (ms)")
        plt.grid(True)

        out_path = os.path.join(out_dir, f"{algo}_time.png")
        plt.savefig(out_path)
        plt.close()
        print(f"✅ Saved {out_path}")

    print("📊 All plots generated!")


if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python generate_plots.py <csv_file> <out_dir>")
        sys.exit(1)

    main(sys.argv[1], sys.argv[2])
