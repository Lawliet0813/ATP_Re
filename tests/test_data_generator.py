"""
Test data generator for decoder validation.

This module generates sample packet data that can be used to validate
the Python decoders against the Java reference implementation.
"""

from datetime import datetime
from typing import Dict, List
import json


class TestDataGenerator:
    """Generate test data for decoder validation."""
    
    @staticmethod
    def generate_mmi_dynamic_packet() -> Dict:
        """
        Generate a sample MMI_DYNAMIC packet with known values.
        
        Returns:
            Dictionary containing packet data and expected decoded values
        """
        # Create packet with known values
        packet_data = bytes([
            # Header (15 bytes)
            0x01,  # packet_no = 1 (ATP/MMI)
            0x17, 0x0A, 0x0F, 0x0E, 0x1E, 0x2D,  # 2023-10-15 14:30:45
            0x00, 0x00, 0x03, 0xE8,  # location = 1000 meters
            0x00, 0x00,  # reserved
            0x00, 0x78,  # speed = 120 km/h
            
            # Body length
            0x1E,  # 30 bytes
            
            # MMI_DYNAMIC body
            0x01,  # subpacket type = MMI_DYNAMIC
            0x00, 0x00,  # placeholder
            0x00, 0x78,  # v_train = 120 km/h
            0x00, 0x0A,  # a_train = 10 cm/s²
            0x00, 0x00, 0x03, 0xE8,  # o_train = 1000 meters
            0x00, 0x00, 0x07, 0xD0,  # o_brake_target = 2000 meters
            0x00, 0x64,  # v_target = 100 km/h
            0x00, 0x1E,  # t_interven_war = 30 seconds
            0x00, 0x82,  # v_permitted = 130 km/h
            0x00, 0x6E,  # v_release = 110 km/h
            0x00, 0x8C,  # v_intervention = 140 km/h
            0x50,  # m_warning=5, m_slip=0, m_slide=0
            0x00, 0x00, 0x0B, 0xB8,  # o_bcsp = 3000 meters
        ])
        
        expected = {
            "header": {
                "packet_no": 1,
                "timestamp": "2023-10-15 14:30:45",
                "location": 1000,
                "speed": 120
            },
            "mmi_dynamic": {
                "v_train": 120,
                "a_train": 10,
                "o_train": 1000,
                "o_brake_target": 2000,
                "v_target": 100,
                "t_interven_war": 30,
                "v_permitted": 130,
                "v_release": 110,
                "v_intervention": 140,
                "m_warning": 5,
                "m_slip": 0,
                "m_slide": 0,
                "o_bcsp": 3000
            }
        }
        
        return {
            "name": "MMI_DYNAMIC_Test_1",
            "packet_hex": packet_data.hex(),
            "packet_bytes": list(packet_data),
            "expected": expected
        }
    
    @staticmethod
    def generate_btm_fragments() -> Dict:
        """
        Generate a complete set of BTM telegram fragments.
        
        Returns:
            Dictionary containing 5 fragments and expected telegram
        """
        sequence_no = 42
        fragments = []
        
        # Fragment 1: sequence + 25 bytes (4 bytes will be used for telegram header)
        frag1 = bytes([sequence_no] + [0x11] * 25)
        fragments.append({
            "telegram_number": 1,
            "packet_hex": frag1.hex(),
            "packet_bytes": list(frag1)
        })
        
        # Fragments 2-5: sequence + 25 bytes each
        for i in range(2, 6):
            frag = bytes([sequence_no] + [0x11 * i] * 25)
            fragments.append({
                "telegram_number": i,
                "packet_hex": frag.hex(),
                "packet_bytes": list(frag)
            })
        
        # Expected reassembled telegram (104 bytes)
        telegram_data = bytearray(104)
        telegram_data[0:4] = bytes([0x11] * 4)  # From fragment 1
        telegram_data[4:29] = bytes([0x22] * 25)  # From fragment 2
        telegram_data[29:54] = bytes([0x33] * 25)  # From fragment 3
        telegram_data[54:79] = bytes([0x44] * 25)  # From fragment 4
        telegram_data[79:104] = bytes([0x55] * 25)  # From fragment 5
        
        return {
            "name": "BTM_Reassembly_Test_1",
            "sequence_number": sequence_no,
            "fragments": fragments,
            "expected_telegram_hex": bytes(telegram_data).hex(),
            "expected_telegram_size": 104
        }
    
    @staticmethod
    def generate_all_test_data() -> Dict:
        """
        Generate all test data for validation.
        
        Returns:
            Dictionary containing all test cases
        """
        return {
            "version": "1.0",
            "generated_at": datetime.now().isoformat(),
            "test_cases": [
                TestDataGenerator.generate_mmi_dynamic_packet(),
                TestDataGenerator.generate_btm_fragments(),
            ]
        }
    
    @staticmethod
    def save_test_data(filename: str = "test_data.json") -> None:
        """
        Save test data to a JSON file.
        
        Args:
            filename: Output filename
        """
        test_data = TestDataGenerator.generate_all_test_data()
        with open(filename, 'w') as f:
            json.dump(test_data, f, indent=2)
        print(f"Test data saved to {filename}")


if __name__ == "__main__":
    # Generate and save test data
    TestDataGenerator.save_test_data("/tmp/decoder_test_data.json")
    
    # Also print a sample for verification
    print("\nSample MMI_DYNAMIC packet:")
    sample = TestDataGenerator.generate_mmi_dynamic_packet()
    print(f"Hex: {sample['packet_hex']}")
    print(f"Expected: {json.dumps(sample['expected'], indent=2)}")
