# -*- mode: python ; coding: utf-8 -*-
"""
PyInstaller spec file for ATP_Re Launcher
打包 ATP_Re 啟動器
"""

block_cipher = None

# Analysis: 分析依賴和收集檔案
a = Analysis(
    ['launcher.py'],
    pathex=[],
    binaries=[],
    datas=[],
    hiddenimports=[
        'socket',
        'subprocess',
        'webbrowser',
        'logging',
    ],
    hookspath=[],
    hooksconfig={},
    runtime_hooks=[],
    excludes=[
        'tkinter',
        'matplotlib',
        'IPython',
        'jupyter',
    ],
    win_no_prefer_redirects=False,
    win_private_assemblies=False,
    cipher=block_cipher,
    noarchive=False,
)

# PYZ: 建立 Python zip 壓縮檔
pyz = PYZ(a.pure, a.zipped_data, cipher=block_cipher)

# EXE: 建立執行檔
exe = EXE(
    pyz,
    a.scripts,
    a.binaries,
    a.zipfiles,
    a.datas,
    [],
    name='ATP_Re',
    debug=False,
    bootloader_ignore_signals=False,
    strip=False,
    upx=True,
    upx_exclude=[],
    runtime_tmpdir=None,
    console=True,  # 顯示控制台視窗
    disable_windowed_traceback=False,
    target_arch=None,
    codesign_identity=None,
    entitlements_file=None,
    icon=None,  # 可以加入自訂圖示
)
