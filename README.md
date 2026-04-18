# 弈剑行 源码 — 导入到 GitHub（说明）

本仓库根目录已添加常用的 `.gitignore`，以排除 Unity/Android 的临时与构建产物。

快速步骤：

1.（可选）配置 Git 用户信息（只需执行一次）：

```
git config --global user.name "你的名字"
git config --global user.email "you@example.com"
```

2. 初始化并提交：

```
git init
git branch -M main
git add .
git commit -m "Initial import of project files"
```

3. 创建远程并推送：

- 方法 A（推荐，使用 GitHub CLI）：

```
gh repo create <OWNER>/<REPO> --public --source=. --remote=origin --push
```

- 方法 B（手动通过 GitHub 网站）：在 GitHub 上新建仓库，然后：

```
git remote add origin https://github.com/<USER>/<REPO>.git
git push -u origin main
```

4. 关于大文件：GitHub 单文件限制为 100MB。如有较大二进制文件（.apk、.so、.unity3d 等），请使用 Git LFS：

```
git lfs install
git lfs track "*.apk"
git lfs track "*.so"
git add .gitattributes
git commit -m "Track large files with LFS"
```

如果你希望我代为创建远程仓库并推送，请回复“帮我创建远程”，我会继续（需要授权或远程仓库 URL）。
