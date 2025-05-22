git checkout -f main
git reset --hard origin/main
git clean -df
rmdir site/src/jsMain/kotlin/dev/bitspittle/kotlinconf25/kobweb/pages/demo 2>/dev/null
rmdir site/src/jvmMain/kotlin/dev/bitspittle/kotlinconf25/kobweb/api/guestbook 2>/dev/null

