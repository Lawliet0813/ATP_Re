"""
Byte utility functions for ATP decoders.

This module provides utilities for converting byte arrays to numbers,
matching the Java Byte2Number functionality.
"""

from typing import Union


class Byte2Number:
    """
    Utility class for converting bytes to numbers.
    
    This class provides static methods to convert byte arrays to signed
    and unsigned integers, matching the behavior of the Java Byte2Number class.
    """
    
    @staticmethod
    def get_unsigned(b: Union[int, bytes]) -> int:
        """
        Get unsigned integer value from a single byte.
        
        Args:
            b: Single byte value (int in range -128 to 127 or 0 to 255)
            
        Returns:
            Unsigned integer value (0 to 255)
        """
        if isinstance(b, bytes):
            b = b[0]
        return b & 0xFF
    
    @staticmethod
    def get_unsigned_2(b1: Union[int, bytes], b2: Union[int, bytes]) -> int:
        """
        Get unsigned 16-bit integer from two bytes (big-endian).
        
        Args:
            b1: First byte (most significant)
            b2: Second byte (least significant)
            
        Returns:
            Unsigned 16-bit integer value (0 to 65535)
        """
        if isinstance(b1, bytes):
            b1 = b1[0]
        if isinstance(b2, bytes):
            b2 = b2[0]
        return ((b1 & 0xFF) << 8) | (b2 & 0xFF)
    
    @staticmethod
    def get_unsigned_3(b1: Union[int, bytes], b2: Union[int, bytes], 
                       b3: Union[int, bytes]) -> int:
        """
        Get unsigned 24-bit integer from three bytes (big-endian).
        
        Args:
            b1: First byte (most significant)
            b2: Second byte
            b3: Third byte (least significant)
            
        Returns:
            Unsigned 24-bit integer value (0 to 16777215)
        """
        if isinstance(b1, bytes):
            b1 = b1[0]
        if isinstance(b2, bytes):
            b2 = b2[0]
        if isinstance(b3, bytes):
            b3 = b3[0]
        return ((b1 & 0xFF) << 16) | ((b2 & 0xFF) << 8) | (b3 & 0xFF)
    
    @staticmethod
    def get_unsigned_4(b1: Union[int, bytes], b2: Union[int, bytes],
                       b3: Union[int, bytes], b4: Union[int, bytes]) -> int:
        """
        Get unsigned 32-bit integer from four bytes (big-endian).
        
        Args:
            b1: First byte (most significant)
            b2: Second byte
            b3: Third byte
            b4: Fourth byte (least significant)
            
        Returns:
            Unsigned 32-bit integer value (0 to 4294967295)
        """
        if isinstance(b1, bytes):
            b1 = b1[0]
        if isinstance(b2, bytes):
            b2 = b2[0]
        if isinstance(b3, bytes):
            b3 = b3[0]
        if isinstance(b4, bytes):
            b4 = b4[0]
        return ((b1 & 0xFF) << 24) | ((b2 & 0xFF) << 16) | ((b3 & 0xFF) << 8) | (b4 & 0xFF)
    
    @staticmethod
    def get_signed_2(b1: Union[int, bytes], b2: Union[int, bytes]) -> int:
        """
        Get signed 16-bit integer from two bytes (big-endian).
        
        Args:
            b1: First byte (most significant)
            b2: Second byte (least significant)
            
        Returns:
            Signed 16-bit integer value (-32768 to 32767)
        """
        unsigned = Byte2Number.get_unsigned_2(b1, b2)
        # Convert to signed if MSB is set
        if unsigned >= 0x8000:
            return unsigned - 0x10000
        return unsigned
    
    @staticmethod
    def get_signed_3(b1: Union[int, bytes], b2: Union[int, bytes], 
                     b3: Union[int, bytes]) -> int:
        """
        Get signed 24-bit integer from three bytes (big-endian).
        
        Args:
            b1: First byte (most significant)
            b2: Second byte
            b3: Third byte (least significant)
            
        Returns:
            Signed 24-bit integer value (-8388608 to 8388607)
        """
        unsigned = Byte2Number.get_unsigned_3(b1, b2, b3)
        # Convert to signed if MSB is set
        if unsigned >= 0x800000:
            return unsigned - 0x1000000
        return unsigned
    
    @staticmethod
    def get_signed_4(b1: Union[int, bytes], b2: Union[int, bytes],
                     b3: Union[int, bytes], b4: Union[int, bytes]) -> int:
        """
        Get signed 32-bit integer from four bytes (big-endian).
        
        Args:
            b1: First byte (most significant)
            b2: Second byte
            b3: Third byte
            b4: Fourth byte (least significant)
            
        Returns:
            Signed 32-bit integer value (-2147483648 to 2147483647)
        """
        unsigned = Byte2Number.get_unsigned_4(b1, b2, b3, b4)
        # Convert to signed if MSB is set
        if unsigned >= 0x80000000:
            return unsigned - 0x100000000
        return unsigned
