#!/usr/bin/env bash
set -euo pipefail

# Requirements: java (JDK 8+) and curl
CFR_JAR=cfr.jar
CFR_URL="https://www.benf.org/other/cfr/cfr.jar"

if [ ! -f "$CFR_JAR" ]; then
  echo "Downloading CFR decompiler..."
  curl -sSfL -o "$CFR_JAR" "$CFR_URL"
fi

OUTDIR="decompiled"
mkdir -p "$OUTDIR"

# Decompile jar files
echo "Decompiling jar files..."
find . -type f -name '*.jar' -print0 | while IFS= read -r -d '' J; do
  safe=$(echo "$J" | sed 's#^\./##; s#[/ ]#_#g')
  target="$OUTDIR/jar_$safe"
  mkdir -p "$target"
  echo "  -> $J  => $target"
  java -jar "$CFR_JAR" --outputdir "$target" "$J"
done

# Decompile individual .class files
echo "Decompiling individual .class files..."
find . -type f -name '*.class' -print0 | while IFS= read -r -d '' C; do
  rel=$(realpath --relative-to="." "$C" 2>/dev/null || printf "%s" "$C")
  outdir="$OUTDIR/classes/$(dirname "$rel")"
  mkdir -p "$outdir"
  echo "  -> $C  => $outdir"
  java -jar "$CFR_JAR" --outputdir "$outdir" "$C"
done

echo "Decompilation finished. Results in ./$OUTDIR"
