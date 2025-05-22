git checkout snapshot/kcFe1
git rebase main

git checkout snapshot/kcFe2
git rebase snapshot/kcFe1

git checkout snapshot/kcFe3
git rebase snapshot/kcFe2

git checkout snapshot/kcBe
git rebase snapshot/kcFe3
