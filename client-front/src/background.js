'use strict'

import { app, protocol, BrowserWindow, ipcMain, dialog, ipcRenderer } from 'electron'
import { createProtocol } from 'vue-cli-plugin-electron-builder/lib'
import installExtension, { VUEJS3_DEVTOOLS } from 'electron-devtools-installer'
const path = require('path')
const ProgressBar = require('electron-progressbar');
const Datastore = require('nedb');
const fs = require('fs');
const { exec } = require('child_process');

const isDevelopment = process.env.NODE_ENV !== 'production'
let win;
let loginWin;
let menuWin;

// Scheme must be registered before the app is ready
protocol.registerSchemesAsPrivileged([
  { scheme: 'app', privileges: { secure: true, standard: true } }
])


async function createWindow() {
  // Create the browser window.
  win = new BrowserWindow({
    // width: 500,
    // height: 500,
    width: 1920,
    height: 1080,
    frame: false,
    resizable: false,
    alwaysOnTop: true,
    fullscreen: true,
    minimizable: false,
    skipTaskbar: true,
    title: "ICIA PC",
    webPreferences: {
      preload: path.join(__dirname, 'preload.js'),
      nodeIntegration: process.env.ELECTRON_NODE_INTEGRATION,
      contextIsolation: !process.env.ELECTRON_NODE_INTEGRATION
    }
  })

  // win.progressBar = new ProgressBar({
  //     indeterminate: false,
  //     text: `ICIA PC ver ${app.getVersion()}`,
  //     detail: '공대장 피씨방'
  // });

  /* 새로고침 막음 */
  win.webContents.on('before-input-event', (event, input) => {
    if (input.type === 'keyDown' && input.key === 'R' && (input.control || input.meta)) {
      event.preventDefault();
    }
  })

  win.splash = new BrowserWindow({
      width: 400, height: 300, frame: false,
  })
  win.splash.loadURL(path.join(__dirname, 'splash.html'))

  if (process.env.WEBPACK_DEV_SERVER_URL) {
    // Load the url of the dev server if in development mode
    await win.loadURL(process.env.WEBPACK_DEV_SERVER_URL)
    // if (!process.env.IS_TEST) win.webContents.openDevTools()
  } else {
    createProtocol('app')
    // Load the index.html when not in development
    win.loadURL('app://./index.html')
  }
}


async function createMenuWin() {
  menuWin = new BrowserWindow({
    width:400
    , height: 50
    , frame: false
    , resizable: false
    , y: 0
    , skipTaskbar: true
    , webPreferences: {
      preload: path.join(__dirname, 'preload.js'),
      nodeIntegration: process.env.ELECTRON_NODE_INTEGRATION,
      contextIsolation: !process.env.ELECTRON_NODE_INTEGRATION
    }
  });
  if (process.env.WEBPACK_DEV_SERVER_URL) {
    menuWin.loadURL(process.env.WEBPACK_DEV_SERVER_URL)
    // if (!process.env.IS_TEST) win.webContents.openDevTools()
  } else {
    menuWin.loadURL('app://./index.html')
  }
  
  menuWin.webContents.on('did-finish-load', () => {
    menuWin.webContents.send('navigate-to-header');
  });
  
  menuWin.once('ready-to-show', () => {
    const [currentX, _] = menuWin.getPosition();
    menuWin.setPosition(currentX, 0);
    menuWin.show();
  });
  
}


function createOrderWin() {
  menuWin.orderWin = new BrowserWindow({
    width:1400
    , height: 850
    , frame: false
    , resizable: false
    , y: 100
    , skipTaskbar: true
    , webPreferences: {
      preload: path.join(__dirname, 'preload.js'),
      nodeIntegration: process.env.ELECTRON_NODE_INTEGRATION,
      contextIsolation: !process.env.ELECTRON_NODE_INTEGRATION
    }
  });
  if (process.env.WEBPACK_DEV_SERVER_URL) {
    menuWin.orderWin.loadURL(process.env.WEBPACK_DEV_SERVER_URL)
  } else {
    menuWin.orderWin.loadURL('app://./index.html')
  }
  
  menuWin.orderWin.webContents.on('did-finish-load', () => {
    menuWin.orderWin.webContents.send('navigate-to-order');
  });
  
  menuWin.orderWin.show();
  
}

function createChargeWin() {
  menuWin.chargeWin = new BrowserWindow({
    width:1400
    , height: 450
    , frame: false
    , resizable: false
    , y: 100
    , skipTaskbar: true
    , webPreferences: {
      preload: path.join(__dirname, 'preload.js'),
      nodeIntegration: process.env.ELECTRON_NODE_INTEGRATION,
      contextIsolation: !process.env.ELECTRON_NODE_INTEGRATION
    }
  });
  if (process.env.WEBPACK_DEV_SERVER_URL) {
    menuWin.chargeWin.loadURL(process.env.WEBPACK_DEV_SERVER_URL)
  } else {
    menuWin.chargeWin.loadURL('app://./index.html')
  }
  
  menuWin.chargeWin.webContents.on('did-finish-load', () => {
    menuWin.chargeWin.webContents.send('navigate-to-charge');
  });
  
  menuWin.chargeWin.show();
  
}


ipcMain.on('saveToFile', (event, pcNumWrapper) => {
  const filePath = path.join(`${app.getPath('userData')}`, "pcNum.txt");
  const directory = path.dirname(filePath);
  const pcNumber = pcNumWrapper.pcNum;
  if(!fs.existsSync(directory)){
    fs.mkdirSync(directory, { recursive: true });
  }

  fs.writeFileSync(filePath, pcNumber);
});

ipcMain.on('readFromFile', (event) => {
  const filePath = path.join(`${app.getPath('userData')}`, 'pcNum.txt');
  const directory = path.dirname(filePath);

  if(fs.existsSync(directory)){
    // 파일에서 데이터 읽어오기
    const data = fs.readFileSync(filePath, 'utf-8');

    // 읽어온 데이터를 Vue.js 컴포넌트로 전달
    win.webContents.send('fileData', data);
  }
});

ipcMain.on('confirmAndShutdown', (event, data) => {
  // dialog.showMessageBox({
  //   type: 'question',
  //   buttons: ['종료', 'Cancel'],
  //   defaultId: 0,
  //   title: '시스템 종료',
  //   message: '시스템 종료 하시겠습니까?',
  // }).then((result) => {
  //   if (result.response === 0) {
  //     // 'Shutdown' 버튼이 눌렸을 때 시스템 종료 명령 실행
      // exec('shutdown /s /t 0', (error, stdout, stderr) => {
        // if (error) {
        //   console.error(`Error executing shutdown command: ${error}`);
        // }
      // });
    // }
  // });
  switch(data) {
    case "shutdown" :
      exec('shutdown /s /t 0', (error, stdout, stderr) => {})
    break;
    case "restart" :
      exec('shutdown /r /t 0', (error, stdout, stderr) => {})
    break;
  }
});

ipcMain.on('createLoginWindow', (event) => {
  loginWin = new BrowserWindow({
    width:380
    , height: 900
    , frame: false
    , resizable: false
    , x: 1100
    , y: 50
    , skipTaskbar: true
    , transparent: true
    , webPreferences: {
      preload: path.join(__dirname, 'preload.js'),
      nodeIntegration: process.env.ELECTRON_NODE_INTEGRATION,
      contextIsolation: !process.env.ELECTRON_NODE_INTEGRATION
    }
  });

  if (process.env.WEBPACK_DEV_SERVER_URL) {
    loginWin.loadURL(process.env.WEBPACK_DEV_SERVER_URL)
    // if (!process.env.IS_TEST) win.webContents.openDevTools()
  } else {
    loginWin.loadURL('app://./index.html')
  }
  createMenuWin();
  loginWin.show();
  win.close();
})


ipcMain.on('checkMainWin', (event) => {
  if(win !== null && !win.isDestroyed()){
    loginWin = new BrowserWindow({
      width:380
      , height: 900
      , frame: false
      , resizable: false
      , x: 1100
      , y: 50
      , transparent: true
      , skipTaskbar: true
      , webPreferences: {
        preload: path.join(__dirname, 'preload.js'),
        nodeIntegration: process.env.ELECTRON_NODE_INTEGRATION,
        contextIsolation: !process.env.ELECTRON_NODE_INTEGRATION
      }
    });
  
    if (process.env.WEBPACK_DEV_SERVER_URL) {
      loginWin.loadURL(process.env.WEBPACK_DEV_SERVER_URL)
      // if (!process.env.IS_TEST) win.webContents.openDevTools()
    } else {
      loginWin.loadURL('app://./index.html')
    }
    loginWin.show();
    win.close();
    createMenuWin();
  }
})
  

ipcMain.on('logout', (event) => {
  // dialog.showMessageBox({
  //   type: 'question',
  //   buttons: ['종료', 'Cancel'],
  //   defaultId: 0,
  //   title: '사용 종료',
  //   message: '사용 종료 하시겠습니까?',
  // }).then((result) => {
  //   if (result.response === 0) {
      createWindow();
      win.splash.close();
      menuWin.close();
      loginWin.close();
  //   }
  // });
})
ipcMain.on('closeOrderWin', (event) => {
  menuWin.orderWin.close();
})

ipcMain.on('createOrderWin', (event) => {
  createOrderWin();
})

ipcMain.on('closeChargeWin', (event) => {
  menuWin.chargeWin.close();
})
ipcMain.on('createChargeWin', (event) => {
  createChargeWin();
})


// Quit when all windows are closed.
app.on('window-all-closed', () => {
  // On macOS it is common for applications and their menu bar
  // to stay active until the user quits explicitly with Cmd + Q
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

app.on('activate', () => {
  // On macOS it's common to re-create a window in the app when the
  // dock icon is clicked and there are no other windows open.
  if (BrowserWindow.getAllWindows().length === 0) createWindow()
})

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on('ready', async () => {
  ipcMain.on('read-file-request', (event, filePath) => {
    fs.readFile(path.join(`${app.getPath('userData')}`, "pcNum.txt"), 'utf-8', (err, data) => {
      if (err) {
        console.error('Error reading the file:', err);
        return;
      }
      console.log('File content:', data);
    })

  })
  if (isDevelopment && !process.env.IS_TEST) {
    // Install Vue Devtools
    try {
      await installExtension(VUEJS3_DEVTOOLS)
    } catch (e) {
      console.error('Vue Devtools failed to install:', e.toString())
    }
  }
  createWindow()
  setTimeout(function() {
    if (win.splash) win.splash.close()
  }, 1000)
})

app.on('before-quit', (event) => {
  // event.preventDefault();
});

// Exit cleanly on request from parent process in development mode.
if (isDevelopment) {
  if (process.platform === 'win32') {
    process.on('message', (data) => {
      if (data === 'graceful-exit') {
        app.quit()
      }
    })
  } else {
    process.on('SIGTERM', () => {
      app.quit()
    })
  }
}

