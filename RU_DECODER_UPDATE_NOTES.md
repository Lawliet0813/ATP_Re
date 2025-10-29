# RU Decoder Update Notes

## Overview
Updated RU decoder implementation to comply with specification **ATPRU-LOGF-001 RU Log File Data Format version 1.8** (dated 2006-03-24).

## Document Reference
The specification document used for this update: `24_ATPRU-LOGF-001_RU_Log_File_Data_Format-1.8.pdf`

## Changes Made

### 1. Fixed Speed Stamp Reading in Record Header
**Files Modified:**
- `decoder_re/HeadDecoder.java`
- `com/MiTAC/TRA/ATP/decoder/HeadDecoder.java`

**Issue:** 
The speed stamp was incorrectly read from bytes 13-14 (2 bytes) using `MMI_V_TRAIN()` method.

**Correction:**
According to the specification, the RU record structure is:
- Byte 0: Log Data Type (1 byte)
- Bytes 1-6: Time Stamp (6 bytes: year, month, day, hour, min, sec)
- Bytes 7-10: Location Stamp (4 bytes, signed long, Unit: cm)
- **Bytes 11-14: Speed Stamp (4 bytes, signed long, Unit: cm/s)**
- Byte 15: Data length (1 byte)
- Bytes 16+: Data information (variable)

The speed stamp is now correctly read from bytes 11-14 as a 4-byte signed integer.

**Code Change:**
```java
// Before:
this.speed = this.mmivariables.MMI_V_TRAIN(tsb[13], tsb[14]);

// After:
this.speed = Byte2Number.getSigned(tsb[11], tsb[12], tsb[13], tsb[14]);
```

### 2. Fixed T_ATP_DOWN_DIS_SPEED_TIME Data Parsing
**Files Modified:**
- `decoder_re/RU.java`
- `decoder_re/RUDecoder.java`
- `decode_re/RU.java`
- `com/MiTAC/TRA/ATP/decode/RU.java`
- `com/MiTAC/TRA/ATP/decoder/RUDecoder.java`

**Issue:**
The T_ATP_DOWN_DIS_SPEED_TIME (type 201) data was incorrectly reading speed from bytes 6-7 (2 bytes).

**Correction:**
According to the specification, T_ATP_DOWN_DIS_SPEED_TIME has 8 bytes of data:
- **Bytes 0-3: Location (4 bytes, unsigned int, Unit: mm)**
- **Bytes 4-7: Speed (4 bytes, signed int, Unit: cm/s)**

The speed is now correctly read from bytes 4-7 as a 4-byte signed integer.

**Code Change:**
```java
// Before:
int spd = this.mmivairalbes.MMI_V_TRAIN(data[6], data[7]);

// After:
int spd = Byte2Number.getSigned(data[4], data[5], data[6], data[7]);
```

## Data Type Constants Verification

All RU Log Data Type constants have been verified against the specification:

| Constant Name | Value | MVB Port | Status |
|--------------|-------|----------|--------|
| MVB_LOG_TYPE_ATP_MMI_CH1 | 1 | 2312-2314, 2304 | ✓ Verified |
| T_STATUS_ATP | 2 | N/A | ✓ Verified |
| T_STATUS_MMI | 3 | N/A | ✓ Verified |
| MVB_LOG_TYPE_ATP_MMI_CH2 | 4 | 2328-2330, 2320 | ✓ Verified |
| MVB_LOG_TYPE_VDX_IN_STATUS_1 | 21 | 384 | ✓ Verified |
| MVB_LOG_TYPE_VDX_OUT_1 | 22 | 392 | ✓ Verified |
| MVB_LOG_TYPE_VDX_OUT_2 | 23 | 393 | ✓ Verified |
| MVB_LOG_TYPE_VDX_OUT_3 | 24 | 394 | ✓ Verified |
| MVB_LOG_TYPE_DX_IN_STATUS_1 | 31 | 2176 | ✓ Verified |
| MVB_LOG_TYPE_DX_STATUS_1 | 32 | 2177 | ✓ Verified |
| MVB_LOG_TYPE_DX_OUT_STATUS_1 | 33 | 2184 | ✓ Verified |
| MVB_LOG_BTM_COMMAND_1 | 41 | 648 | ✓ Verified |
| MVB_LOG_BTM_STATUS_1 | 42 | 642 | ✓ Verified |
| MVB_LOG_BTM_TGM_1 | 43 | 643 | ✓ Verified |
| MVB_LOG_BTM_TGM_2 | 44 | 644 | ✓ Verified |
| MVB_LOG_BTM_TGM_3 | 45 | 645 | ✓ Verified |
| MVB_LOG_BTM_TGM_4 | 46 | 646 | ✓ Verified |
| MVB_LOG_BTM_TGM_5 | 47 | 647 | ✓ Verified |
| MVB_LOG_SDU1 | 51 | 256 | ✓ Verified |
| MVB_LOG_SDU2 | 52 | 272 | ✓ Verified |
| MVB_LOG_ODO_CONFIG_1 | 61 | 1283 | ✓ Verified |
| MVB_LOG_ODO_MESSAGE_1 | 62 | 1285 | ✓ Verified |
| MVB_LOG_ODO_MESSAGE_2 | 63 | 1286 | ✓ Verified |
| MVB_LOG_ODO_BTM_STATUS_1 | 64 | 1284 | ✓ Verified |
| MVB_LOG_PM_LOG_TGM | 71 | 100 | ✓ Verified |
| MVB_LOG_PM_APP_LOG_TGM | 72 | 1287 | ✓ Verified |
| T_PRS_INFO | 91 | N/A | ✓ Verified |
| T_ATP_DOWN_DIS_SPEED_TIME | 201 | N/A | ✓ Verified |
| T_PERIODIC_SPEED_DISTANCE | 211 | N/A | ✓ Verified |
| T_BUTTON_EVENT | 216 | N/A | ✓ Verified |
| T_STATUS_COUNTER_BOARD | 221 | N/A | ✓ Verified |
| T_STATUS_USB | 222 | N/A | ✓ Verified |
| T_STATUS_PRS | 223 | N/A | ✓ Verified |
| T_STATUS_SPEEDMETER | 224 | N/A | ✓ Verified |
| T_STATUS_DATA_DOWNLOAD | 225 | N/A | ✓ Verified |
| T_STATUS_MVB | 227 | N/A | ✓ Verified |
| T_STATUS_GPP | 228 | N/A | ✓ Verified |

## Impact Analysis

### Breaking Changes
These changes may affect existing parsed data where speed values were previously incorrect:

1. **Speed values will now be different** - The previous implementation was reading only 2 bytes of speed data at the wrong position, resulting in incorrect speed values. After this fix:
   - Speed values in the record header will be read correctly from the full 4-byte field
   - T_ATP_DOWN_DIS_SPEED_TIME speed values will be read from the correct position

2. **Data interpretation** - Any existing databases or analysis tools that relied on the previous incorrect speed values will need to be reviewed and potentially re-processed with the corrected decoder.

### Backward Compatibility
- The file format itself has not changed - only the parser implementation has been corrected
- Existing RU log files can be re-parsed with the corrected decoder
- Data type constants remain the same

## Testing Recommendations

1. **Re-parse existing RU files** - Verify that speed values are now correctly extracted
2. **Compare before/after** - Check a sample of records to see the difference in speed readings
3. **Validate against known data** - If you have RU files with known speed values, verify the decoder now produces correct results
4. **Check downstream systems** - Any systems that consume the decoded data should be tested with the new speed values

## Additional Notes

### Removed Constants
The specification version history (v1.4) indicates that the following were removed:
- `T_STATUS_CF_CARD` 
- `T_MMI_STATUS_REPORT_CHANGED` (value 81)

However, `MMI_STATUS_REPORT_CHANGED = 81` still exists in the code. This constant is not in the v1.8 specification but has been left in the code for potential backward compatibility. Consider removing if not needed.

### Specification Version
This update brings the decoder in compliance with:
- **Document**: ATPRU-LOGF-001
- **Version**: 1.8
- **Date**: 2006-03-24
- **Author**: Justin Ho (MiTAC Inc)

## References
- ATPRU-LOGF-001 RU Log File Data Format v1.8
- Interface Specification, ATPCU - MMI 3NSS003791D0106 v3.2
- Recording Unit (RU) Requirement Specification 3NSS004830D0200 v1.2
