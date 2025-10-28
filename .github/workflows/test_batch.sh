#!/usr/bin/env bash
set -euo pipefail

# ====== 必須修改此處 ======
# PROCESS_CMD 範例（兩種常見範例給你參考，選一種並替換下面變數）
# 本機 binary 範例: ./bin/atp_re process <input> -o <output>
# Docker 範例: docker run --rm -v "$(pwd)":/data atp_re:latest process /data/<input> -o /data/<output>
PROCESS_CMD="./bin/atp_re process"  # <-- 把這行改成你的實際 command（不要包含 input/output 參數）
# ==========================

INPUT_DIR="./tests/input"
OUT_DIR="./tests/output"
EXPECTED_DIR="./tests/expected"
TOLERANCE=0.01  # 數值比對容差（可調）

mkdir -p "$OUT_DIR"

# 找到 .ru 或 .mmi（不分大小寫）
mapfile -t files < <(find "$INPUT_DIR" -type f \( -iname "*.ru" -o -iname "*.mmi" \) | sort)

if [ ${#files[@]} -eq 0 ]; then
  echo "No input files found in $INPUT_DIR"
  exit 1
fi

fail_count=0

for f in "${files[@]}"; do
  base=$(basename "$f")
  out="$OUT_DIR/${base}.json"

  echo "Processing $f -> $out"
  # 假設 PROCESS_CMD 接受 input 跟 -o output，若不同請自行調整這一行
  $PROCESS_CMD "$f" -o "$out"

  expected="$EXPECTED_DIR/${base}.json"
  if [ -f "$expected" ]; then
    python3 compare_results.py "$out" "$expected" --tolerance "$TOLERANCE"
    rc=$?
    if [ $rc -ne 0 ]; then
      echo "DIFF: $base"
      fail_count=$((fail_count+1))
    else
      echo "OK: $base"
    fi
  else
    echo "No expected file for $base (skipping compare)"
  fi
done

echo "Done. failures: $fail_count"
if [ $fail_count -ne 0 ]; then
  exit 2
fi
