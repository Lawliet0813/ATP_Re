"""
Decoder module - Core decoding components for ATP/MMI binary data.

This module contains the fundamental decoders that parse binary RU/MMI files:
- PacketHeaderParser: Parses 15-byte record headers
- MMIDecoder: Decodes 40+ MMI packet types
- BTMDecoder: Decodes BTM telegrams with 5-fragment reassembly
"""
