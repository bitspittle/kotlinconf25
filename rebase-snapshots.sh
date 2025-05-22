git checkout snapshot/kcFe1
git rebase main
git push --force

git checkout snapshot/kcFe2
git rebase snapshot/kcFe1
git push --force

git checkout snapshot/kcFe3
git rebase snapshot/kcFe2
git push --force

git checkout snapshot/kcBe
git rebase snapshot/kcFe3
git push --force

git checkout main
