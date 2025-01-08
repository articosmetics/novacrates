# NovaCrates

[![Platform](https://img.shields.io/badge/Platform-PaperMC%201.21-red.svg?style=flat-square)](https://papermc.io/)
[![Commands API](https://img.shields.io/badge/Commands-Brigadier%20API-blue.svg?style=flat-square)](https://docs.papermc.io/)
[![Java](https://img.shields.io/badge/Java-21-orange.svg?style=flat-square)](https://adoptium.net/)
[![Build](https://img.shields.io/badge/Build-Maven-purple.svg?style=flat-square)](https://maven.apache.org/)

> **NovaCrates** is a high-performance crate rewards engine engineered for modern Paper 1.21+ servers. Features include floating 3D BlockDisplay crate cases with smooth yaw transformations, client-side particle roulette spin animations, virtual key persistence, and administrative GUI editors.

---

## Technical Architecture

### 1. 3D Display Entity Visuals
- Employs Minecraft 1.20+ `BlockDisplay` and `TextDisplay` entities rather than lag-inducing armor stands.
- Floating shulker blocks encased within translucent colored stained-glass shells with customizable scale and glowing outline shaders.
- Client-side billboard holograms with per-player virtual key count reflections and dynamic action cues.

### 2. High-Throughput Roulette Spin Sequence
- Real-time interpolated particle animations with audio pitch inflection as the spin decelerates towards the final prize.
- Asynchronous drop table evaluation supporting fractional percentage probabilities, custom model data items, and broadcast announcements.
- Full protection against inventory close exploits, item dropping glitching, and server restarts during active spins.

### 3. Modern Paper Commands API
- Built entirely on Paper's modern Brigadier Commands API (`io.papermc.paper.command.brigadier`) via lifecycle event registration.
- Native command argument completion and permission validation without legacy `plugin.yml` command bloat.

---

## Commands and Permissions

| Command | Permission | Description |
| :--- | :--- | :--- |
| `/crate` | `novacrates.admin` | Open administrative crate configuration GUI. |
| `/crate create <name> [display]` | `novacrates.admin` | Create a new crate definition. |
| `/crate set <crate>` | `novacrates.admin` | Bind target block in line of sight to a crate. |
| `/crate key <player> <crate> [amount]` | `novacrates.admin` | Give virtual keys to a player. |
| `/crate keyall <crate> [amount]` | `novacrates.admin` | Grant virtual keys to all online players. |
| `/crate takekey <player> <crate\|all> [amount]` | `novacrates.admin` | Deduct virtual keys from a player. |
| `/crate rename <crate> <display>` | `novacrates.admin` | Update crate display name. |
| `/crate delete <crate>` | `novacrates.admin` | Unregister and delete a crate. |
| `/keys` | None | Open player virtual keys pouch GUI. |

---

## Configuration (`config.yml`)

```yaml
prefix: "&#00d2ff&lNOVACRATES &8● &f"

shrink-shulker: true
shulker-size: 0.8
glass-case: true
glass-size: 1.0
glow: false

half-star-colour: "&#A0762A"

hologram:
  match-colour: true
  height: 0
  scale: 0.8
  lines:
    - "%crate%"
    - "%subtitle%"
    - ""
    - "&#00d2ff▶ &fLeft-Click to &#00d2ffPreview"
    - "&#00d2ff◀ &fRight-Click to &#00d2ffOpen"
    - "&#00d2ff◀ &fShift Right-Click to &#00d2ffOpen All"
  default-subtitle: "&7Get keys @ /store"
  keys-line: "&fYou have &#00d2ff%keys% &fkeys!"

daily-keyall:
  enabled: false
  crate: daily
  amount: 1
  time: "18:00"
  timezone: "Europe/London"
  broadcast: "&7Everyone online got &f%amount%x %crate% &fKey&7! &8(/keys)"
```

---

## Compilation

```bash
git clone https://github.com/articosmetics/novacrates.git
cd novacrates
mvn clean package
```

Output artifact: `target/novacrates-1.2.4.jar`.

---

## Author
Engineered by **Wnderin** ([@articosmetics](https://github.com/articosmetics))  
Available for custom server plugin commissions and network optimization.
