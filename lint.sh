#!/bin/zsh

clear

echo -e "### cljfmt" ; lein cljfmt check && echo "\n### bikeshed" ; lein bikeshed -v && echo "\n### eastwood" ; lein eastwood
