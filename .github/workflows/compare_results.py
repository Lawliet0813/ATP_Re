#!/usr/bin/env python3
import sys
import json
import math
import argparse
from collections.abc import Mapping, Sequence

def load(path):
    with open(path, 'r', encoding='utf-8') as f:
        return json.load(f)

differences = []

def approx_equal(a, b, tol):
    try:
        return math.isclose(float(a), float(b), rel_tol=tol, abs_tol=tol)
    except Exception:
        return False

def compare(a, b, path="", tol=1e-6):
    if type(a) != type(b):
        differences.append(f"{path or '/'} type mismatch: {type(a).__name__} != {type(b).__name__}")
        return
    if isinstance(a, Mapping):
        for key in sorted(set(a.keys()) | set(b.keys())):
            if key not in a:
                differences.append(f"{path}/{key} missing in produced")
            elif key not in b:
                differences.append(f"{path}/{key} missing in expected")
            else:
                compare(a[key], b[key], f"{path}/{key}", tol)
    elif isinstance(a, Sequence) and not isinstance(a, (str, bytes)):
        if len(a) != len(b):
            differences.append(f"{path or '/'} list length {len(a)} != {len(b)}")
        for i, (ai, bi) in enumerate(zip(a, b)):
            compare(ai, bi, f"{path}[{i}]", tol)
    elif isinstance(a, (int, float)) and isinstance(b, (int, float)):
        if not approx_equal(a, b, tol):
            differences.append(f"{path or '/'} number diff: {a} != {b} (tol={tol})")
    else:
        if a != b:
            differences.append(f"{path or '/'} value diff: {a} != {b}")

def main():
    parser = argparse.ArgumentParser(description="Compare produced JSON vs expected JSON")
    parser.add_argument("produced")
    parser.add_argument("expected")
    parser.add_argument("--tolerance", type=float, default=1e-6, help="numeric tolerance")
    args = parser.parse_args()

    prod = load(args.produced)
    exp = load(args.expected)

    compare(prod, exp, "", args.tolerance)

    if differences:
        print("Differences found:")
        for d in differences:
            print(" -", d)
        sys.exit(1)
    else:
        print("No differences")
        sys.exit(0)

if __name__ == "__main__":
    main()
