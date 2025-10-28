# ATP Packet Decoder Tool

A command-line utility for decoding ATP RU (Recording Unit) files and displaying all decoded packet values.

## Features

- Decodes RU packet files into human-readable format
- Displays all decoded packet header and data fields
- Supports multiple output formats (text, JSON)
- Shows field descriptions for all decoded values
- Can save output to file or display to console

## Usage

### Basic Usage

Decode first 10 packets from an RU file:
```bash
python decode_packets.py path/to/file.RU -n 10
```

### Output Formats

**Text format** (human-readable with field descriptions):
```bash
python decode_packets.py path/to/file.RU -f text
```

Example output:
```
--- Packet #1 ---
Packet Type: 1
Description: MMI_DYNAMIC
Header:
  Packet Number/Type: 1
  Recording Timestamp: 2025-09-03T02:44:32
  Train Location (meters): 5139209
  Train Speed (km/h): 2107
Decoded Data:
  Train Speed (km/h): 2107
  Train Acceleration (cm/s²): 0
  Train Position (meters): 5139209
  Brake Target Position (meters): 5221620
  Target Speed (km/h): 2222
  Intervention Warning Time (seconds): 65535
  Permitted Speed (km/h): 2361
  Release Speed (km/h): 65535
  Intervention Speed (km/h): 65535
  Warning Mode (0-15): 0
  Slip Indication (0-1): 0
  Slide Indication (0-1): 0
  BCSP Position (meters): 4294967295
```

**JSON format** (machine-readable):
```bash
python decode_packets.py path/to/file.RU -f json
```

Example output:
```json
[
  {
    "packet_type": 1,
    "description": "MMI_DYNAMIC",
    "header": {
      "packet_no": 1,
      "timestamp": "2025-09-03T02:44:32",
      "location": 5139209,
      "speed": 2107
    },
    "data": {
      "v_train": 2107,
      "a_train": 0,
      "o_train": 5139209,
      "o_brake_target": 5221620,
      "v_target": 2222,
      "t_interven_war": 65535,
      "v_permitted": 2361,
      "v_release": 65535,
      "v_intervention": 65535,
      "m_warning": 0,
      "m_slip": 0,
      "m_slide": 0,
      "o_bcsp": 4294967295
    }
  }
]
```

### Save to File

Save decoded output to a file:
```bash
python decode_packets.py path/to/file.RU -o output.txt
python decode_packets.py path/to/file.RU -f json -o output.json
```

### Advanced Options

```bash
# Decode all packets (no limit)
python decode_packets.py path/to/file.RU

# Decode first 100 packets
python decode_packets.py path/to/file.RU -n 100

# Verbose output (show decoding progress and errors)
python decode_packets.py path/to/file.RU -v
```

## Command-Line Options

| Option | Description | Default |
|--------|-------------|---------|
| `input_file` | Path to RU file (required) | - |
| `-n`, `--max-packets` | Maximum packets to decode | All |
| `-f`, `--format` | Output format (text/json) | text |
| `-o`, `--output` | Output file path | stdout |
| `-v`, `--verbose` | Verbose output | False |

## Decoded Packet Types

The tool decodes the following packet types:

### MMI_DYNAMIC (Type 1)
Dynamic train state information including:
- Train speed, acceleration, position
- Brake target position
- Target, permitted, release, intervention speeds
- Warning modes and slip/slide indications

### MMI_STATUS (Type 2)
Train status information including:
- Adhesion mode
- Operating mode
- ATP level
- Brake statuses
- Trip status

### BTM Telegrams (Types 43-47)
Balise Transmission Module data:
- Telegram fragments (1-5)
- Complete reassembled telegrams
- Balise group identifiers

### Other Packet Types
- ATP packets
- Status packets
- VDX packets
- Button events
- And more...

## Examples

### Example 1: Quick inspection of a file
```bash
python decode_packets.py data/024423.RU -n 5
```

### Example 2: Full decode to JSON file
```bash
python decode_packets.py data/024423.RU -f json -o decoded_packets.json
```

### Example 3: Find specific packet types
```bash
python decode_packets.py data/024423.RU | grep -A 20 "MMI_DYNAMIC"
```

## Python API Usage

You can also use the decoder programmatically:

```python
from src.atp_re.decoders import RUDecoder, PacketFormatter

# Create decoder
decoder = RUDecoder()

# Decode a packet
with open('path/to/file.RU', 'rb') as f:
    packet_data = f.read(100)  # Read packet data
    
result = decoder.decode(packet_data)

# Get as dictionary
packet_dict = result.to_dict()

# Format for display
formatter = PacketFormatter()
print(formatter.format_packet(packet_dict))
```

## Field Descriptions

All decoded fields include human-readable descriptions:

**Header Fields:**
- `packet_no`: Packet Number/Type
- `timestamp`: Recording Timestamp  
- `location`: Train Location (meters)
- `speed`: Train Speed (km/h)

**MMI_DYNAMIC Fields:**
- `v_train`: Train Speed (km/h)
- `a_train`: Train Acceleration (cm/s²)
- `o_train`: Train Position (meters)
- `o_brake_target`: Brake Target Position (meters)
- And many more...

See `src/atp_re/decoders/packet_formatter.py` for complete field descriptions.

## Notes

- Large values (e.g., 65535, 4294967295) often indicate "not available" or "infinite"
- Position values >= 1 billion are automatically adjusted by subtracting 1 billion
- BTM telegrams require all 5 fragments to be reassembled into complete 104-byte telegrams

## Troubleshooting

**Error: File not found**
- Check that the file path is correct
- Use absolute paths if relative paths don't work

**Error: Unable to parse decoded data**
- The file may be corrupted
- Try using the `-v` verbose flag to see more details

**No decoded data for some packets**
- Some packet types don't have detailed decoders yet
- The packet header is always decoded and displayed
