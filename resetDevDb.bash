#!/bin/bash

TARGET="./docker-mount/db-dev/pgdata"
TARGETDIR="$(dirname $TARGET)"
TARGETFILE="$(basename $TARGET)"

dump () {
  echo "$1 : $(ls -l $TARGETDIR | grep $TARGETFILE)"
}
# dump state
dump "Before"

# cleanstep
[[ -f "$TARGET" ]] && sudo rm "$TARGET"
[[ -d "$TARGET" ]] && sudo rm -Rf "$TARGET"

# recreate
mkdir -p "$TARGET"

# done
dump "After "
