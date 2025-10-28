# -*- mode: python ; coding: utf-8 -*-
"""
PyInstaller spec file for ATP_Re API Backend
打包 ATP_Re API 後端
"""

block_cipher = None

# Analysis: 分析依賴和收集檔案
a = Analysis(
    ['api/main.py'],
    pathex=[],
    binaries=[],
    datas=[
        ('api', 'api'),
        ('config', 'config'),
        ('core', 'core'),
        ('src', 'src'),
    ],
    hiddenimports=[
        # FastAPI & Uvicorn
        'uvicorn',
        'uvicorn.logging',
        'uvicorn.loops',
        'uvicorn.loops.auto',
        'uvicorn.protocols',
        'uvicorn.protocols.http',
        'uvicorn.protocols.http.auto',
        'uvicorn.protocols.websockets',
        'uvicorn.protocols.websockets.auto',
        'uvicorn.lifespan',
        'uvicorn.lifespan.on',
        
        # FastAPI
        'fastapi',
        'fastapi.responses',
        'fastapi.middleware',
        'fastapi.middleware.cors',
        
        # Pydantic
        'pydantic',
        'pydantic.v1',
        'pydantic_settings',
        
        # SQLAlchemy with SQLite
        'sqlalchemy',
        'sqlalchemy.dialects',
        'sqlalchemy.dialects.sqlite',
        'sqlalchemy.pool',
        'sqlalchemy.ext',
        'sqlalchemy.ext.declarative',
        
        # Other dependencies
        'aiofiles',
        'python_multipart',
        'websockets',
        
        # Utilities
        'dotenv',
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
        
        # Exclude DB drivers we don't need
        'psycopg2',
        'pymssql',
        'pymongo',
        'redis',
        
        # Exclude monitoring we don't need
        'prometheus_client',
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
    name='ATP_API',
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
    name='ATP_API',
)
