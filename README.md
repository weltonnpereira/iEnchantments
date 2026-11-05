# IEnchants ⚡

[![Spigot Version](https://img.shields.io/badge/Spigot-1.8%20-orange.svg)](https://www.spigotmc.org/)
[![License](https://img.shields.io/badge/License-Proprietary-blue.svg)](https://www.spigotmc.org/resources/ienchantments.140265/)
[![Architecture](https://img.shields.io/badge/Architecture-Modular-success.svg)]()

**IEnchants** is a production-ready, modular custom enchantment and utility engine built for high-performance Minecraft Spigot/Purpur servers. It features an advanced extraction rune system, tiered book mechanics with success rates, and a robust experience recycler economy.

---

## The Engineering Evolution (From 2021 to 2026)

This repository represents a **complete, ground-up rewrite in 2026**.

Originally prototyped back in 2021 as a monolithic, tightly-coupled script filled with redundant listeners and hardcoded string checks, `IEnchants` has evolved into a clean, maintainable, and highly optimized software architecture.

By eliminating legacy spaghetti code, centralizing business logic into dedicated managers (`PlManager` pattern), and introducing rigorous safety checks (such as inventory overflow protection and strict target item validation), this rewrite reflects professional growth, architectural maturity, and a commitment to zero-lag server performance.

---

## Core Architecture

The plugin is structured around a centralized plugin manager (`PlManager`) that injects dependencies and coordinates specialized subsystems, ensuring a **Single Source of Truth** for all enchantment rules and metadata:

- **`EnchantmentManager`**: Centralizes all enchantment registries, tier mappings, success-rate calculations, and item compatibility validations.
- **`RuneManager`**: Handles Extraction Rune logic, scanning items for enchantments, and converting them into safe books while preventing cursor interference.
- **`RecyclerManager`**: Manages the custom GUI economy, computing proportion-based XP conversions (15%–60% of base costs) and guaranteeing zero item loss during inventory overflows.

---

## Key Features

- **Modular & Event-Driven**: Clean separation of concerns between UI interactions, inventory events, and core logic, avoiding performance bottlenecks.
- **Extraction Runes**: Safely extract existing enchantments from gear into books, with built-in slot validation and overflow protection.
- **Tiered Book System**: Simple, Normal, and Advanced enchantment books featuring dynamic success percentages and strict target equipment filtering.
- **Advanced XP Recycler GUI**: Players can deposit unwanted enchanted books to transform them into experience bottles with safe item devolution (`dropItem` fallback if inventories are full).
- **Clean Internationalization**: Fully localized UI elements, error messages, and item lores in standard English.

---

## Tech Stack & Design Patterns

- **Language**: Java (Spigot API)
- **Architecture Pattern**: Manager-Service Pattern with Centralized Dependency Control
- **State Management**: Safe ItemStack metadata serialization using custom Display Names and Lore parsing
- **Robust Error Handling**: Advanced safe-guarding against inventory duplication exploits and full-inventory overflow loss

---

_Maintained by [Wp Voxel](https://github.com/weltonnpereira)_
