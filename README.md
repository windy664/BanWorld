<div align="center">

<img src="assets/banner.svg" alt="BanWorld" width="100%">

# BanWorld

**世界黑白名单** —— 黑名单或白名单两种模式，控制玩家能进入哪些世界。

![Platform](https://img.shields.io/badge/Platform-Spigot%20%2F%20Paper-62bda8?style=flat-square&logo=minecraft&logoColor=white)
![Minecraft](https://img.shields.io/badge/MC-1.20.x-8a9a5b?style=flat-square)
![Java](https://img.shields.io/badge/Java-17-f89820?style=flat-square&logo=openjdk&logoColor=white)

[功能](#-功能) · [配置](#️-配置)

</div>

## ✨ 功能

- **黑名单模式**：`blacklisted_worlds` 中的世界禁止进入（默认开启）
- **白名单模式**：只允许进入 `whitelisted_worlds` 列出的世界（`whitelist_enabled` 开启）
- **双模式互斥**：黑/白名单独立开关，按服务器管理习惯选择
- **入场拦截**：监听世界切换/传送事件，命中即取消并提示

## ⚙️ 配置

```yaml
blacklisted_worlds:
  - world_nether
  - world_the_end
whitelisted_worlds:
  - world
blacklist_enabled: true
whitelist_enabled: false
debug: false
```

## 🔨 构建

```bash
./gradlew build
```
