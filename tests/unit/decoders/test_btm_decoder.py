"""
Unit tests for BTMDecoder fragment reassembly.
"""

import pytest
from atp_re.decoders.btm_decoder import BTMDecoder, BTMTelegram, BTMFragment


class TestBTMDecoder:
    """Test cases for BTM fragment reassembly."""
    
    def test_single_sequence_reassembly(self):
        """Test reassembling 5 fragments of a single sequence."""
        decoder = BTMDecoder()
        
        # Create 5 fragments with sequence number 42
        sequence_no = 42
        
        # Fragment 1: sequence + 25 bytes of data (bytes 22-25 will be used)
        frag1 = bytes([sequence_no] + [0x11] * 25)
        
        # Fragments 2-5: sequence + 25 bytes of data (bytes 1-25 will be used)
        frag2 = bytes([sequence_no] + [0x22] * 25)
        frag3 = bytes([sequence_no] + [0x33] * 25)
        frag4 = bytes([sequence_no] + [0x44] * 25)
        frag5 = bytes([sequence_no] + [0x55] * 25)
        
        # Add fragments 1-4, should return None (incomplete)
        result = decoder.add_fragment(frag1, 1)
        assert result is None
        
        result = decoder.add_fragment(frag2, 2)
        assert result is None
        
        result = decoder.add_fragment(frag3, 3)
        assert result is None
        
        result = decoder.add_fragment(frag4, 4)
        assert result is None
        
        # Add fragment 5, should complete and return telegram
        result = decoder.add_fragment(frag5, 5)
        
        assert result is not None
        assert isinstance(result, BTMTelegram)
        assert result.sequence_number == sequence_no
        assert len(result.data) == 104
        
        # Verify data was reassembled correctly
        # First 4 bytes from fragment 1 (bytes 22-25)
        assert result.data[0:4] == bytes([0x11] * 4)
        # Next 25 bytes from fragment 2
        assert result.data[4:29] == bytes([0x22] * 25)
        # Next 25 bytes from fragment 3
        assert result.data[29:54] == bytes([0x33] * 25)
        # Next 25 bytes from fragment 4
        assert result.data[54:79] == bytes([0x44] * 25)
        # Last 25 bytes from fragment 5
        assert result.data[79:104] == bytes([0x55] * 25)
    
    def test_out_of_order_fragments(self):
        """Test that fragments can arrive out of order."""
        decoder = BTMDecoder()
        sequence_no = 10
        
        # Create fragments with distinct patterns
        frag1 = bytes([sequence_no] + [0x01] * 25)
        frag2 = bytes([sequence_no] + [0x02] * 25)
        frag3 = bytes([sequence_no] + [0x03] * 25)
        frag4 = bytes([sequence_no] + [0x04] * 25)
        frag5 = bytes([sequence_no] + [0x05] * 25)
        
        # Add in non-sequential order: 3, 1, 5, 2, 4
        assert decoder.add_fragment(frag3, 3) is None
        assert decoder.add_fragment(frag1, 1) is None
        assert decoder.add_fragment(frag5, 5) is None
        assert decoder.add_fragment(frag2, 2) is None
        
        # Last fragment completes reassembly
        result = decoder.add_fragment(frag4, 4)
        
        assert result is not None
        assert result.sequence_number == sequence_no
        assert len(result.data) == 104
    
    def test_multiple_sequences_interleaved(self):
        """Test handling multiple sequence numbers interleaved."""
        decoder = BTMDecoder()
        
        seq1 = 10
        seq2 = 20
        
        # Create fragments for two different sequences
        seq1_frag1 = bytes([seq1] + [0x11] * 25)
        seq1_frag2 = bytes([seq1] + [0x12] * 25)
        seq2_frag1 = bytes([seq2] + [0x21] * 25)
        seq2_frag2 = bytes([seq2] + [0x22] * 25)
        
        # Interleave fragments from two sequences
        assert decoder.add_fragment(seq1_frag1, 1) is None
        assert decoder.add_fragment(seq2_frag1, 1) is None
        assert decoder.add_fragment(seq1_frag2, 2) is None
        assert decoder.add_fragment(seq2_frag2, 2) is None
        
        # Complete remaining fragments for seq1
        seq1_frag3 = bytes([seq1] + [0x13] * 25)
        seq1_frag4 = bytes([seq1] + [0x14] * 25)
        seq1_frag5 = bytes([seq1] + [0x15] * 25)
        
        assert decoder.add_fragment(seq1_frag3, 3) is None
        assert decoder.add_fragment(seq1_frag4, 4) is None
        result1 = decoder.add_fragment(seq1_frag5, 5)
        
        assert result1 is not None
        assert result1.sequence_number == seq1
        
        # Complete remaining fragments for seq2
        seq2_frag3 = bytes([seq2] + [0x23] * 25)
        seq2_frag4 = bytes([seq2] + [0x24] * 25)
        seq2_frag5 = bytes([seq2] + [0x25] * 25)
        
        assert decoder.add_fragment(seq2_frag3, 3) is None
        assert decoder.add_fragment(seq2_frag4, 4) is None
        result2 = decoder.add_fragment(seq2_frag5, 5)
        
        assert result2 is not None
        assert result2.sequence_number == seq2
    
    def test_duplicate_fragments(self):
        """Test that duplicate fragments are handled correctly."""
        decoder = BTMDecoder()
        sequence_no = 5
        
        frag1 = bytes([sequence_no] + [0x01] * 25)
        frag2 = bytes([sequence_no] + [0x02] * 25)
        
        # Add fragment 1 twice
        assert decoder.add_fragment(frag1, 1) is None
        assert decoder.add_fragment(frag1, 1) is None  # Duplicate
        
        # Continue with remaining fragments
        assert decoder.add_fragment(frag2, 2) is None
        assert decoder.add_fragment(bytes([sequence_no] + [0x03] * 25), 3) is None
        assert decoder.add_fragment(bytes([sequence_no] + [0x04] * 25), 4) is None
        result = decoder.add_fragment(bytes([sequence_no] + [0x05] * 25), 5)
        
        # Should still complete successfully
        assert result is not None
        assert result.sequence_number == sequence_no
    
    def test_slot_reuse(self):
        """Test that slots are reused after completion."""
        decoder = BTMDecoder()
        
        # Complete first telegram
        seq1 = 100
        for i in range(1, 6):
            frag = bytes([seq1] + [i] * 25)
            result = decoder.add_fragment(frag, i)
        
        # Slot should be freed and reusable
        seq2 = 101
        for i in range(1, 6):
            frag = bytes([seq2] + [i + 10] * 25)
            result = decoder.add_fragment(frag, i)
            if i == 5:
                assert result is not None
                assert result.sequence_number == seq2
    
    def test_invalid_telegram_number(self):
        """Test that invalid telegram numbers raise errors."""
        decoder = BTMDecoder()
        frag = bytes([42] + [0x01] * 25)
        
        with pytest.raises(ValueError, match="Invalid telegram number"):
            decoder.add_fragment(frag, 0)
        
        with pytest.raises(ValueError, match="Invalid telegram number"):
            decoder.add_fragment(frag, 6)
        
        with pytest.raises(ValueError, match="Invalid telegram number"):
            decoder.add_fragment(frag, -1)
    
    def test_fragment_too_short(self):
        """Test that too-short fragments raise errors."""
        decoder = BTMDecoder()
        frag = bytes([])  # Empty
        
        with pytest.raises(ValueError, match="Fragment data too short"):
            decoder.add_fragment(frag, 1)
    
    def test_get_last_telegram(self):
        """Test retrieving the last completed telegram."""
        decoder = BTMDecoder()
        
        # Initially no telegram
        assert decoder.get_last_telegram() is None
        
        # Complete a telegram
        sequence_no = 42
        for i in range(1, 6):
            frag = bytes([sequence_no] + [i] * 25)
            decoder.add_fragment(frag, i)
        
        # Should have last telegram
        last = decoder.get_last_telegram()
        assert last is not None
        assert last.sequence_number == sequence_no
    
    def test_reset_decoder(self):
        """Test resetting decoder state."""
        decoder = BTMDecoder()
        
        # Add some fragments
        sequence_no = 42
        for i in range(1, 4):
            frag = bytes([sequence_no] + [i] * 25)
            decoder.add_fragment(frag, i)
        
        # Reset
        decoder.reset()
        
        # All state should be cleared
        assert decoder.get_last_telegram() is None
        assert decoder.get_pending_sequences() == []
    
    def test_get_pending_sequences(self):
        """Test getting pending (incomplete) sequences."""
        decoder = BTMDecoder()
        
        # Add partial fragments for sequence 10
        seq1 = 10
        for i in range(1, 4):  # Only 3 of 5 fragments
            frag = bytes([seq1] + [i] * 25)
            decoder.add_fragment(frag, i)
        
        # Add partial fragments for sequence 20
        seq2 = 20
        for i in range(1, 3):  # Only 2 of 5 fragments
            frag = bytes([seq2] + [i] * 25)
            decoder.add_fragment(frag, i)
        
        pending = decoder.get_pending_sequences()
        
        assert len(pending) == 2
        # Convert to dict for easier checking
        pending_dict = dict(pending)
        assert pending_dict[seq1] == 3  # 3 fragments received
        assert pending_dict[seq2] == 2  # 2 fragments received
    
    def test_max_slots(self):
        """Test that decoder can handle up to 10 concurrent sequences."""
        decoder = BTMDecoder()
        
        # Start 10 different sequences (max slots)
        for seq in range(10):
            frag = bytes([seq] + [0x01] * 25)
            result = decoder.add_fragment(frag, 1)
            assert result is None  # All incomplete
        
        # Verify 10 pending sequences
        pending = decoder.get_pending_sequences()
        assert len(pending) == 10
        
        # Try to add 11th sequence - existing implementation will fail
        # This is expected behavior matching Java implementation
        frag11 = bytes([100] + [0x01] * 25)
        result = decoder.add_fragment(frag11, 1)
        # Should still return None (no available slot)
        assert result is None
