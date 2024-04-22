# Silentium Spigot Plugin

Silentium is a Spigot plugin specifically designed to protect Minecraft servers from server scanner bots by utilizing an IP blacklist. It fetches the blacklist from a maintained list at [PebbleHost's Hunter repository](https://github.com/pebblehost/hunter) and blocks incoming pings from these IPs.

## Features

- **IP Blacklist**: Automatically fetches and updates an IP blacklist from the PebbleHost Hunter repository.
- **Packet Filtering**: Uses PacketEvents to intercept and examine network packets, rejecting those from blacklisted IPs.
- **Efficient Caching**: Implements Caffeine caching to manage the IP blacklist efficiently, reducing the need for frequent updates.

## Installation

1. **Download the Plugin**:
   - Download the latest version of Silentium from the Releases section on GitHub.
2. **Server Setup**:
   - Place the downloaded ```.jar``` file into your server's ```plugins``` directory.
3. **Restart Server**:
   - Restart your server to ensure the plugin is loaded properly. Check the server logs to confirm that there are no errors during startup.

## Configuration

Silentium generates a ```config.yml``` file in the ```plugins/Silentium``` directory on its first run. You can modify this file to adjust plugin settings.

### Sample ```config.yml```:

```yaml
# Enable or disable debug messages
Debug: false

# Time in minutes to cache the IP blacklist before refreshing
Cache_Time: 360
```

## Usage

Once installed and configured, Silentium will begin to automatically block packets from IP addresses listed in the blacklist. The plugin operates in the background, requiring no further manual intervention.

## Building from Source

### Prerequisites

- Java 8
- Gradle

### Build Instructions

To build the plugin from source:

1. Clone the repository:
   ```bash
   git clone https://github.com/MrMcyeet/Silentium.git
   ```
2. Navigate to the project directory:
   ```bash
   cd Silentium
   ```
3. Run the Gradle shadowJar task to build the plugin:
   ```bash
   ./gradlew shadowJar
   ```

The built JAR file will be located in the ```build/libs``` directory.

## Contributing

Contributions to Silentium are welcome, including improvements to the codebase, bug reports, and feature requests. Please use GitHub's issues and pull requests features to contribute.

## License

This project is licensed under the GNU General Public License v3.0. A copy of the license is included in the root of the project's directory in the ```LICENSE``` file. The full terms of the license are also available online at [https://www.gnu.org/licenses/gpl-3.0.html](https://www.gnu.org/licenses/gpl-3.0.html).
