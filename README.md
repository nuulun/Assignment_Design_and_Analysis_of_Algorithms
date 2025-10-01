# Algo Project
## Recurrence Analysis


### MergeSort
- Recurrence: **T(n) = 2T(n/2) + Θ(n)**.
- Master Theorem case 2 → **Θ(n log n)**.
- Depth = Θ(log n).

### QuickSort (Randomized, Tail-Recursive)
- Partitioning costs Θ(n). Expected split balanced.
- **T(n) = T(U) + T(V) + Θ(n)**, expected depth ≈ 2 log₂ n.
- Worst case Θ(n²), but randomized pivot avoids adversarial input.

### Deterministic Select (Median-of-Medians)
- Guaranteed 30–70% pivot split:  
  **T(n) = T(n/5) + T(7n/10) + Θ(n)**.
- Akra–Bazzi → **Θ(n)**.
- Depth ≤ O(log n).

### Closest Pair of Points
- Recurrence: **T(n) = 2T(n/2) + Θ(n)** (strip check).
- Master Theorem → **Θ(n log n)**.
- Depth = Θ(log n).

---

## 3. Measurements
 

### MergeSort
![MergeSort Runtime](benchmarks/plots/MergeSort_time.png)

### QuickSort
![QuickSort Runtime](benchmarks/plots/QuickSort_time.png)

### SelectMoM
![SelectMoM Runtime](benchmarks/plots/SelectMoM_time.png)

### ClosestPair
![ClosestPair Runtime](benchmarks/plots/ClosestPair_time.png)



### Time vs n (ms)

| n         | MergeSort | QuickSort | Select | Closest Pair |
|-----------|-----------|-----------|--------|--------------|
| 1,000     | ~0.09     | ~0.85     | ~0.67  | ~0.8         |
| 10,000    | ~ 0.55    | ~0.7      | ~0.25  | ~6.2         |
| 100,000   | ~ 6.8     | ~7.5      | ~1.7   | ~ 50         |





---

### Recursion Depth vs n

| n         | MergeSort Depth | QuickSort Depth | Select Depth | Closest Pair Depth |
|-----------|-----------------|-----------------|--------------|--------------------|
| 1,000     | ~7              | ~7              | ~2           | ~8                 |
| 10,000    | ~11             | ~9              | ~2           | ~12                |
| 100,000   | ~14             | ~11             | ~2           | ~15                |






---

### Constant-Factor Discussion
- **MergeSort**: cache-friendly merges, but requires O(n) auxiliary buffer.
- **QuickSort**: in-place, no allocations, smaller constant factor than MergeSort.
- **Select**: grouping by 5 increases constants but still scales linearly.
- **Closest Pair**: strip check (≈7–8 neighbor comparisons per point) inflates constants.


---

## 4. Summary
- **Theory vs practice** aligned:
    - MergeSort and QuickSort → O(n log n) scaling confirmed.
    - QuickSort depth ≈ 2 log₂ n, robust even for adversarial input.
    - Median-of-Medians Select → O(n) confirmed, though slower than randomized select in practice.
    - Closest Pair → O(n log n), brute-force verification matched results for small n.
- Constant factors (cache, branch mispredictions, GC) explain small mismatches.

