# SMP - Minecraft Spigot Plugin

**SMP** is a custom PvP-oriented plugin developed for **Spigot 1.18**, designed to introduce a **life-based health system** to your server. Inspired by hardcore multiplayer experiences, this plugin adds a competitive survival twist where every death truly matters.

> ⚠️ **Disclaimer**: This plugin is **quite old** and was built as a learning project. The code does **not reflect modern development standards** and would be written differently today. Use as-is or as a base for future improvements.

---

## Features

- ❤️ **Life-Based Health System**
  - Each life grants additional **maximum HP**.
  - Players **start with a set number of lives**.
  - Upon **death**, a player **loses one life**.
  - The **killer gains one life** (and thus more HP).
  - When a player runs out of lives, they can **no longer join the server**.

- 🔁 **Life Transfer**
  - Players can **transfer lives** to others via command.

- 📊 **Life Overview**
  - Players can **check their current number of lives** at any time.

---

## Commands
/life
- Shows your current life count.

/lifetransfer <player> <amount>
- Transfers a given number of lives to another player.

## Life System Logic

- Every **1 life = +1 heart**
- Lives are persistent and stored per player.
- When a player reaches **0 lives**, they are **locked out** from rejoining the server.

> 🛑 Note: The plugin does not include a built-in way to reset lives. This must be done manually in player data or via admin tools.

---

## Compatibility

- ✅ Minecraft Version: **1.18.x**
- ✅ Server Software: **Spigot**
- ✅ Build Tool: **Maven**

---

## Disclaimer

This plugin was created as a fun experiment and is **no longer actively maintained**. Some features may be buggy or incomplete, and the code is considered outdated. It may not be compatible with newer Minecraft versions without modification.
