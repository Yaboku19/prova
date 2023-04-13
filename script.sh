#!/bin/sh
git filter-branch --env-filter '
OLD_EMAIL="emanuele.martelli5studio.unibo.it"
CORRECT_NAME="Yaboku19"
CORRECT_EMAIL="emanuele.martelli5@studio.unibo.it"
if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_COMMITTER_NAME="$CORRECT_NAME"
    export GIT_COMMITTER_EMAIL="$CORRECT_EMAIL"
fi
if [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_AUTHOR_NAME="$CORRECT_NAME"
    export GIT_AUTHOR_EMAIL="$CORRECT_EMAIL"
fi
' --tag-name-filter cat -- --branches --tags
