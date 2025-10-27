"""
Unit tests for Balise models.
"""

import pytest
from datetime import datetime
from atp_re.models.balise import (
    Balise, BaliseType, TelegramType, BaliseInfo,
    BTMFragment, BTMAssembler
)


class TestBalise:
    """Test cases for Balise model."""
    
    def test_create_balise(self):
        """Test creating a balise record."""
        timestamp = datetime.now()
        telegram = b'\x01\x02\x03\x04'
        
        balise = Balise(
            mission_id=1,
            timestamp=timestamp,
            balise_id="B001",
            balise_type=BaliseType.FIXED,
            position=123.5,
            telegram_data=telegram,
            telegram_type=TelegramType.BALISE
        )
        
        assert balise.mission_id == 1
        assert balise.balise_id == "B001"
        assert balise.balise_type == BaliseType.FIXED
        assert balise.position == 123.5
        assert balise.telegram_data == telegram
        assert balise.is_valid is True
    
    def test_balise_type_from_string(self):
        """Test that balise type can be created from string."""
        balise = Balise(
            mission_id=1,
            timestamp=datetime.now(),
            balise_id="B001",
            balise_type="fixed",
            position=123.5,
            telegram_data=b'\x01\x02'
        )
        
        assert balise.balise_type == BaliseType.FIXED
    
    def test_invalid_telegram_data(self):
        """Test that non-bytes telegram data raises an error."""
        with pytest.raises(ValueError, match="telegram_data must be bytes"):
            Balise(
                mission_id=1,
                timestamp=datetime.now(),
                balise_id="B001",
                balise_type=BaliseType.FIXED,
                position=123.5,
                telegram_data="not bytes"
            )
    
    def test_balise_with_error(self):
        """Test creating a balise with an error."""
        balise = Balise(
            mission_id=1,
            timestamp=datetime.now(),
            balise_id="B001",
            balise_type=BaliseType.FIXED,
            position=123.5,
            telegram_data=b'',
            is_valid=False,
            error_code="CRC_ERROR"
        )
        
        assert balise.is_valid is False
        assert balise.error_code == "CRC_ERROR"


class TestBaliseInfo:
    """Test cases for BaliseInfo model."""
    
    def test_create_balise_info(self):
        """Test creating decoded balise information."""
        info = BaliseInfo(
            balise_id=1,
            position=123.5,
            speed_limit=80.0,
            gradient=2.5,
            track_condition="NORMAL"
        )
        
        assert info.balise_id == 1
        assert info.speed_limit == 80.0
        assert info.gradient == 2.5
    
    def test_balise_info_repr(self):
        """Test string representation of balise info."""
        info = BaliseInfo(
            balise_id=1,
            position=123.5,
            speed_limit=80.0,
            gradient=2.5
        )
        
        repr_str = repr(info)
        assert "BaliseInfo" in repr_str
        assert "id=1" in repr_str
        assert "pos=123.5" in repr_str


class TestBTMFragment:
    """Test cases for BTMFragment model."""
    
    def test_create_fragment(self):
        """Test creating a BTM fragment."""
        timestamp = datetime.now()
        fragment = BTMFragment(
            mission_id=1,
            timestamp=timestamp,
            fragment_number=1,
            total_fragments=3,
            balise_id="B001",
            fragment_data=b'\x01\x02\x03'
        )
        
        assert fragment.fragment_number == 1
        assert fragment.total_fragments == 3
        assert fragment.is_complete is False
    
    def test_invalid_fragment_number(self):
        """Test that invalid fragment number raises an error."""
        with pytest.raises(ValueError, match="fragment_number must be between"):
            BTMFragment(
                mission_id=1,
                timestamp=datetime.now(),
                fragment_number=0,
                total_fragments=3,
                balise_id="B001",
                fragment_data=b'\x01'
            )
        
        with pytest.raises(ValueError, match="fragment_number must be between"):
            BTMFragment(
                mission_id=1,
                timestamp=datetime.now(),
                fragment_number=4,
                total_fragments=3,
                balise_id="B001",
                fragment_data=b'\x01'
            )


class TestBTMAssembler:
    """Test cases for BTMAssembler."""
    
    def test_assemble_fragments(self):
        """Test assembling BTM fragments."""
        assembler = BTMAssembler()
        timestamp = datetime.now()
        
        # Add first fragment
        frag1 = BTMFragment(
            mission_id=1,
            timestamp=timestamp,
            fragment_number=1,
            total_fragments=3,
            balise_id="B001",
            fragment_data=b'\x01\x02'
        )
        result = assembler.add_fragment(frag1)
        assert result is None  # Not complete yet
        
        # Add second fragment
        frag2 = BTMFragment(
            mission_id=1,
            timestamp=timestamp,
            fragment_number=2,
            total_fragments=3,
            balise_id="B001",
            fragment_data=b'\x03\x04'
        )
        result = assembler.add_fragment(frag2)
        assert result is None  # Still not complete
        
        # Add third fragment
        frag3 = BTMFragment(
            mission_id=1,
            timestamp=timestamp,
            fragment_number=3,
            total_fragments=3,
            balise_id="B001",
            fragment_data=b'\x05\x06'
        )
        result = assembler.add_fragment(frag3)
        assert result is not None
        assert result == b'\x01\x02\x03\x04\x05\x06'
    
    def test_assemble_out_of_order(self):
        """Test that fragments can be assembled out of order."""
        assembler = BTMAssembler()
        timestamp = datetime.now()
        
        # Add fragments out of order
        frag3 = BTMFragment(
            mission_id=1,
            timestamp=timestamp,
            fragment_number=3,
            total_fragments=3,
            balise_id="B001",
            fragment_data=b'\x05\x06'
        )
        assembler.add_fragment(frag3)
        
        frag1 = BTMFragment(
            mission_id=1,
            timestamp=timestamp,
            fragment_number=1,
            total_fragments=3,
            balise_id="B001",
            fragment_data=b'\x01\x02'
        )
        assembler.add_fragment(frag1)
        
        frag2 = BTMFragment(
            mission_id=1,
            timestamp=timestamp,
            fragment_number=2,
            total_fragments=3,
            balise_id="B001",
            fragment_data=b'\x03\x04'
        )
        result = assembler.add_fragment(frag2)
        
        # Should still assemble correctly
        assert result == b'\x01\x02\x03\x04\x05\x06'
    
    def test_clear_old_fragments(self):
        """Test clearing old fragments."""
        assembler = BTMAssembler()
        old_time = datetime(2024, 1, 1, 10, 0, 0)
        new_time = datetime(2024, 1, 1, 11, 0, 0)
        
        # Add old fragment
        frag = BTMFragment(
            mission_id=1,
            timestamp=old_time,
            fragment_number=1,
            total_fragments=3,
            balise_id="B001",
            fragment_data=b'\x01'
        )
        assembler.add_fragment(frag)
        
        # Clear fragments older than new_time
        assembler.clear_old_fragments(new_time)
        
        # Internal fragments dict should be empty
        assert len(assembler._fragments) == 0
