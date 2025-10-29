# -*- mode: python ; coding: utf-8 -*-
"""
PyInstaller spec file for ATP_Re Streamlit UI
打包 ATP_Re Streamlit 前端
"""

block_cipher = None

# Analysis: 分析依賴和收集檔案
a = Analysis(
    ['streamlit_ui/app.py'],
    pathex=[],
    binaries=[],
    datas=[
        ('streamlit_ui', 'streamlit_ui'),
    ],
    hiddenimports=[
        # Streamlit core
        'streamlit',
        'streamlit.web',
        'streamlit.web.cli',
        'streamlit.runtime',
        'streamlit.runtime.scriptrunner',
        'streamlit.components',
        'streamlit.components.v1',
        
        # Streamlit dependencies
        'altair',
        'altair.vegalite.v4',
        
        # Plotly
        'plotly',
        'plotly.graph_objs',
        'plotly.express',
        'plotly.io',
        
        # Data processing
        'pandas',
        'numpy',
        
        # Requests
        'requests',
        'urllib3',
        
        # JSON & utilities
        'json',
        'datetime',
    ],
    hookspath=[],
    hooksconfig={},
    runtime_hooks=[],
    excludes=[
        # Exclude unnecessary modules
        'tkinter',
        'matplotlib',
        'IPython',
        'jupyter',
        'notebook',
        
        # Exclude DB drivers we don't need in UI
        'psycopg2',
        'pymssql',
        'pymongo',
        'redis',
        'sqlalchemy',
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
    [],
    exclude_binaries=True,
    name='ATP_UI',
    debug=False,
    bootloader_ignore_signals=False,
    strip=False,
    upx=True,
    console=True,  # 顯示控制台視窗
    disable_windowed_traceback=False,
    target_arch=None,
    codesign_identity=None,
    entitlements_file=None,
    icon=None,  # 可以加入自訂圖示
)

# COLLECT: 收集所有檔案
coll = COLLECT(
    exe,
    a.binaries,
    a.zipfiles,
    a.datas,
    strip=False,
    upx=True,
    upx_exclude=[],
    name='ATP_UI',
)
