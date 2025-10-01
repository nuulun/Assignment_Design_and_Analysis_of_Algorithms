import pandas as pd
import matplotlib.pyplot as plt
import sys
import os

def main(csv_file, out_dir):
    os.makedirs(out_dir, exist_ok=True)

    # Читаем CSV, учтём нижний регистр столбцов
    df = pd.read_csv(csv_file)

    # Проверим наличие нужных колонок
    expected_columns = {"algorithm", "n", "timeMs"}
    if not expected_columns.issubset(df.columns):
        sys.exit(1)

    # Для каждого алгоритма построим график времени
    for algo in df["algorithm"].unique():
        subset = df[df["algorithm"] == algo]
        grouped = subset.groupby("n").mean(numeric_only=True)

        plt.figure()
        plt.plot(grouped.index, grouped["timeMs"], marker="o")
        plt.title(f"{algo} - Runtime")
        plt.xlabel("Input size (N)")
        plt.ylabel("Time (ms)")
        plt.grid(True)

        out_path = os.path.join(out_dir, f"{algo}_time.png")
        plt.savefig(out_path)
        plt.close()
        print(f"✅ Saved {out_path}")

    

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python generate_plots.py <csv_file> <out_dir>")
        sys.exit(1)

    main(sys.argv[1], sys.argv[2])
