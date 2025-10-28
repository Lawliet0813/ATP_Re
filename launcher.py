#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
ATP_Re Standalone Launcher
ATP_Re 單機版啟動器

This script launches both the API backend and Streamlit UI,
and automatically opens the default web browser.

本腳本啟動 API 後端和 Streamlit UI，並自動開啟預設瀏覽器。
"""

import os
import sys
import subprocess
import webbrowser
import time
import socket
import logging
from pathlib import Path
from typing import Optional

# 設定日誌
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.StreamHandler(sys.stdout),
        logging.FileHandler('atp_re_launcher.log', encoding='utf-8')
    ]
)
logger = logging.getLogger(__name__)


class ATPLauncher:
    """ATP_Re 啟動器"""
    
    def __init__(self):
        self.app_dir = self._get_app_dir()
        self.api_process: Optional[subprocess.Popen] = None
        self.ui_process: Optional[subprocess.Popen] = None
        self.api_port = 8000
        self.ui_port = 8501
        
    def _get_app_dir(self) -> Path:
        """取得應用程式目錄"""
        if getattr(sys, 'frozen', False):
            # PyInstaller 打包後的路徑
            return Path(sys._MEIPASS).parent
        else:
            # 開發環境路徑
            return Path(__file__).parent.absolute()
    
    def _is_port_available(self, port: int) -> bool:
        """檢查端口是否可用"""
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            try:
                s.bind(('localhost', port))
                return True
            except OSError:
                return False
    
    def _wait_for_service(self, port: int, timeout: int = 30) -> bool:
        """等待服務啟動"""
        start_time = time.time()
        while time.time() - start_time < timeout:
            try:
                with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
                    s.settimeout(1)
                    result = s.connect_ex(('localhost', port))
                    if result == 0:
                        return True
            except Exception:
                pass
            time.sleep(0.5)
        return False
    
    def _get_executable_path(self, component: str) -> Optional[Path]:
        """取得執行檔路徑"""
        if sys.platform == 'win32':
            exe_name = f'{component}.exe'
        else:
            exe_name = component
        
        # 打包後的路徑
        exe_path = self.app_dir / component / exe_name
        if exe_path.exists():
            return exe_path
        
        # 嘗試其他可能的路徑
        exe_path = self.app_dir / exe_name
        if exe_path.exists():
            return exe_path
        
        logger.error(f"找不到 {component} 執行檔")
        return None
    
    def start_api(self) -> bool:
        """啟動 API 後端"""
        logger.info("正在啟動 API 後端...")
        
        # 檢查端口
        if not self._is_port_available(self.api_port):
            logger.error(f"端口 {self.api_port} 已被佔用")
            return False
        
        # 取得執行檔路徑
        api_exe = self._get_executable_path('ATP_API')
        if not api_exe:
            return False
        
        try:
            # 啟動 API
            self.api_process = subprocess.Popen(
                [str(api_exe)],
                stdout=subprocess.PIPE,
                stderr=subprocess.PIPE,
                cwd=str(self.app_dir)
            )
            
            # 等待 API 啟動
            if self._wait_for_service(self.api_port, timeout=30):
                logger.info(f"✅ API 後端已啟動 (http://localhost:{self.api_port})")
                return True
            else:
                logger.error("API 後端啟動超時")
                self.stop_api()
                return False
                
        except Exception as e:
            logger.error(f"啟動 API 失敗: {e}")
            return False
    
    def start_ui(self) -> bool:
        """啟動 Streamlit UI"""
        logger.info("正在啟動 Streamlit UI...")
        
        # 檢查端口
        if not self._is_port_available(self.ui_port):
            logger.error(f"端口 {self.ui_port} 已被佔用")
            return False
        
        # 取得執行檔路徑
        ui_exe = self._get_executable_path('ATP_UI')
        if not ui_exe:
            return False
        
        try:
            # 啟動 UI
            self.ui_process = subprocess.Popen(
                [str(ui_exe)],
                stdout=subprocess.PIPE,
                stderr=subprocess.PIPE,
                cwd=str(self.app_dir)
            )
            
            # 等待 UI 啟動
            if self._wait_for_service(self.ui_port, timeout=30):
                logger.info(f"✅ Streamlit UI 已啟動 (http://localhost:{self.ui_port})")
                return True
            else:
                logger.error("Streamlit UI 啟動超時")
                self.stop_ui()
                return False
                
        except Exception as e:
            logger.error(f"啟動 UI 失敗: {e}")
            return False
    
    def open_browser(self):
        """開啟瀏覽器"""
        url = f'http://localhost:{self.ui_port}'
        logger.info(f"正在開啟瀏覽器: {url}")
        try:
            webbrowser.open(url)
        except Exception as e:
            logger.warning(f"無法自動開啟瀏覽器: {e}")
            logger.info(f"請手動開啟瀏覽器並訪問: {url}")
    
    def stop_api(self):
        """停止 API 後端"""
        if self.api_process:
            logger.info("正在停止 API 後端...")
            try:
                self.api_process.terminate()
                self.api_process.wait(timeout=5)
            except subprocess.TimeoutExpired:
                self.api_process.kill()
            self.api_process = None
    
    def stop_ui(self):
        """停止 Streamlit UI"""
        if self.ui_process:
            logger.info("正在停止 Streamlit UI...")
            try:
                self.ui_process.terminate()
                self.ui_process.wait(timeout=5)
            except subprocess.TimeoutExpired:
                self.ui_process.kill()
            self.ui_process = None
    
    def stop_all(self):
        """停止所有服務"""
        self.stop_ui()
        self.stop_api()
    
    def run(self):
        """執行啟動器"""
        print("=" * 60)
        print("🚆 ATP_Re - Automatic Train Protection Record Analysis")
        print("=" * 60)
        print()
        
        try:
            # 啟動 API
            if not self.start_api():
                print("❌ API 後端啟動失敗")
                return False
            
            # 啟動 UI
            if not self.start_ui():
                print("❌ Streamlit UI 啟動失敗")
                self.stop_api()
                return False
            
            # 開啟瀏覽器
            time.sleep(2)  # 等待 UI 完全準備好
            self.open_browser()
            
            print()
            print("=" * 60)
            print("✅ ATP_Re 系統已成功啟動！")
            print("=" * 60)
            print()
            print(f"📊 Web UI:        http://localhost:{self.ui_port}")
            print(f"🔧 API 文檔:      http://localhost:{self.api_port}/docs")
            print(f"📋 API 健康檢查:  http://localhost:{self.api_port}/health")
            print()
            print("=" * 60)
            print("按 Ctrl+C 或關閉此視窗以停止系統")
            print("=" * 60)
            print()
            
            # 保持運行
            try:
                while True:
                    # 檢查進程是否還在運行
                    if self.api_process and self.api_process.poll() is not None:
                        logger.error("API 進程意外終止")
                        break
                    if self.ui_process and self.ui_process.poll() is not None:
                        logger.error("UI 進程意外終止")
                        break
                    time.sleep(1)
            except KeyboardInterrupt:
                print("\n\n正在關閉系統...")
            
            return True
            
        except Exception as e:
            logger.error(f"執行錯誤: {e}")
            return False
        
        finally:
            self.stop_all()
            print("✅ 系統已關閉")


def main():
    """主函式"""
    launcher = ATPLauncher()
    success = launcher.run()
    sys.exit(0 if success else 1)


if __name__ == '__main__':
    main()
