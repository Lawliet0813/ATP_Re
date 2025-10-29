"""
Unit tests for BTMDecoder.

Phase 1 tests focus on fragment handling and reassembly logic.
Full packet decoding tests will be added in Phase 2.
"""

import pytest

from src.decoder.btm_decoder import (
    BTMDecoder,
    BTMDecodeError,
    BTMFragment,
    BTMTelegram,
    ETCSPacketType,
)


class TestBTMFragment:
    """Test BTMFragment dataclass."""
    
    def test_fragment_creation(self):
        """Test creating a BTM fragment."""
        fragment = BTMFragment(
            fragment_number=1,
            total_fragments=3,
            data=b'\x01\x02\x03\x04'
        )
        
        assert fragment.fragment_number == 1
        assert fragment.total_fragments == 3
        assert fragment.data == b'\x01\x02\x03\x04'


class TestBTMTelegram:
    """Test BTMTelegram dataclass."""
    
    def test_telegram_creation(self):
        """Test creating a BTM telegram."""
        fragments = [
            BTMFragment(1, 2, b'\x01\x02'),
            BTMFragment(2, 2, b'\x03\x04'),
        ]
        
        telegram = BTMTelegram(
            telegram_id=1001,
            fragments=fragments,
            data=b'\x01\x02\x03\x04',
        )
        
        assert telegram.telegram_id == 1001
        assert len(telegram.fragments) == 2
        assert telegram.data == b'\x01\x02\x03\x04'
        assert telegram.packets is None


class TestBTMDecoder:
    """Test suite for BTMDecoder."""
    
    def setup_method(self):
        """Set up test fixtures."""
        self.decoder = BTMDecoder()
    
    def test_decoder_initialization(self):
        """Test decoder initializes with empty buffer."""
        assert len(self.decoder.fragment_buffer) == 0
        assert self.decoder.get_pending_telegrams() == []
    
    def test_add_single_fragment_telegram(self):
        """Test adding a single-fragment telegram."""
        fragment = BTMFragment(
            fragment_number=1,
            total_fragments=1,
            data=b'\x01\x02\x03\x04'
        )
        
        telegram = self.decoder.add_fragment(fragment, telegram_id=1001)
        
        assert telegram is not None
        assert telegram.telegram_id == 1001
        assert len(telegram.fragments) == 1
        assert telegram.data == b'\x01\x02\x03\x04'
    
    def test_add_two_fragment_telegram(self):
        """Test adding a two-fragment telegram."""
        fragment1 = BTMFragment(
            fragment_number=1,
            total_fragments=2,
            data=b'\x01\x02'
        )
        fragment2 = BTMFragment(
            fragment_number=2,
            total_fragments=2,
            data=b'\x03\x04'
        )
        
        # First fragment - should return None (incomplete)
        result1 = self.decoder.add_fragment(fragment1, telegram_id=1001)
        assert result1 is None
        assert 1001 in self.decoder.get_pending_telegrams()
        
        # Second fragment - should return complete telegram
        result2 = self.decoder.add_fragment(fragment2, telegram_id=1001)
        assert result2 is not None
        assert result2.telegram_id == 1001
        assert len(result2.fragments) == 2
        assert result2.data == b'\x01\x02\x03\x04'
        assert 1001 not in self.decoder.get_pending_telegrams()
    
    def test_add_five_fragment_telegram(self):
        """Test adding a five-fragment telegram (maximum per Java spec)."""
        fragments = [
            BTMFragment(1, 5, b'\x01\x02'),
            BTMFragment(2, 5, b'\x03\x04'),
            BTMFragment(3, 5, b'\x05\x06'),
            BTMFragment(4, 5, b'\x07\x08'),
            BTMFragment(5, 5, b'\x09\x0A'),
        ]
        
        telegram_id = 2001
        
        # Add fragments 1-4, should all return None
        for i in range(4):
            result = self.decoder.add_fragment(fragments[i], telegram_id)
            assert result is None
            assert telegram_id in self.decoder.get_pending_telegrams()
        
        # Add final fragment, should return complete telegram
        result = self.decoder.add_fragment(fragments[4], telegram_id)
        assert result is not None
        assert result.telegram_id == telegram_id
        assert len(result.fragments) == 5
        assert result.data == b'\x01\x02\x03\x04\x05\x06\x07\x08\x09\x0A'
        assert telegram_id not in self.decoder.get_pending_telegrams()
    
    def test_add_fragments_out_of_order(self):
        """Test that fragments can be added in any order."""
        # Add fragments in reverse order: 3, 2, 1
        fragment3 = BTMFragment(3, 3, b'\x05\x06')
        fragment2 = BTMFragment(2, 3, b'\x03\x04')
        fragment1 = BTMFragment(1, 3, b'\x01\x02')
        
        telegram_id = 3001
        
        result1 = self.decoder.add_fragment(fragment3, telegram_id)
        assert result1 is None
        
        result2 = self.decoder.add_fragment(fragment2, telegram_id)
        assert result2 is None
        
        result3 = self.decoder.add_fragment(fragment1, telegram_id)
        assert result3 is not None
        # Despite adding out of order, data should be correctly reassembled
        assert result3.data == b'\x01\x02\x03\x04\x05\x06'
    
    def test_multiple_telegrams_simultaneously(self):
        """Test handling multiple incomplete telegrams at once."""
        # Start telegram 1001 (needs 2 fragments)
        frag_1001_1 = BTMFragment(1, 2, b'\x01\x02')
        result1 = self.decoder.add_fragment(frag_1001_1, 1001)
        assert result1 is None
        
        # Start telegram 2001 (needs 3 fragments)
        frag_2001_1 = BTMFragment(1, 3, b'\x0A\x0B')
        result2 = self.decoder.add_fragment(frag_2001_1, 2001)
        assert result2 is None
        
        # Check both are pending
        pending = self.decoder.get_pending_telegrams()
        assert 1001 in pending
        assert 2001 in pending
        
        # Complete telegram 1001
        frag_1001_2 = BTMFragment(2, 2, b'\x03\x04')
        result3 = self.decoder.add_fragment(frag_1001_2, 1001)
        assert result3 is not None
        assert result3.telegram_id == 1001
        
        # Telegram 2001 still pending
        assert 1001 not in self.decoder.get_pending_telegrams()
        assert 2001 in self.decoder.get_pending_telegrams()
    
    def test_reassemble_telegram_with_invalid_sequence(self):
        """Test that invalid fragment sequences are detected."""
        # Manually create invalid sequence (missing fragment 2)
        decoder = BTMDecoder()
        fragments = [
            BTMFragment(1, 3, b'\x01'),
            BTMFragment(3, 3, b'\x03'),  # Missing fragment 2
        ]
        
        with pytest.raises(BTMDecodeError, match="Invalid fragment sequence"):
            decoder._reassemble_telegram(9999, fragments)
    
    def test_clear_buffer(self):
        """Test clearing the fragment buffer."""
        # Add some incomplete telegrams
        self.decoder.add_fragment(BTMFragment(1, 2, b'\x01'), 1001)
        self.decoder.add_fragment(BTMFragment(1, 3, b'\x02'), 2001)
        
        assert len(self.decoder.get_pending_telegrams()) == 2
        
        # Clear buffer
        self.decoder.clear_buffer()
        
        assert len(self.decoder.get_pending_telegrams()) == 0
        assert len(self.decoder.fragment_buffer) == 0
    
    def test_decode_packets_not_implemented(self):
        """Test that packet decoding raises NotImplementedError (Phase 2)."""
        telegram = BTMTelegram(
            telegram_id=1001,
            fragments=[],
            data=b'\x01\x02\x03'
        )
        
        with pytest.raises(NotImplementedError, match="Phase 2"):
            self.decoder.decode_packets(telegram)


class TestETCSPacketType:
    """Test ETCS packet type constants."""
    
    def test_packet_type_constants(self):
        """Test that ETCS packet type constants are defined."""
        assert ETCSPacketType.P3_MOVEMENT_AUTHORITY == 3
        assert ETCSPacketType.P12_LEVEL_1_MOVEMENT_AUTHORITY == 12
        assert ETCSPacketType.P15_LEVEL_23_MOVEMENT_AUTHORITY == 15
        assert ETCSPacketType.P21_GRADIENT_PROFILE == 21
        assert ETCSPacketType.P27_INTL_STATIC_SPEED_PROFILE == 27
        assert ETCSPacketType.P41_LEVEL_TRANSITION_ORDER == 41
        assert ETCSPacketType.P42_SESSION_MANAGEMENT == 42
        assert ETCSPacketType.P65_TEMPORARY_SPEED_RESTRICTION == 65
