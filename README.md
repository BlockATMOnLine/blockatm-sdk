<div align="center">

<a href="https://blockatm.net/"><img src="https://blockatm.net/assets/images/resources/logo-dark.png" width="477" height="101" alt="BlockATM Logo"></a>

# BlockATM
## 永不触碰您的资金，100%安全</br>

<p align="center">
  <a href="./README_en.md">English</a> |
  <a href="./README_cn.md">简体中文</a> |
  <a href="./README_ja.md">繁體中文</a>
</p>

</div>

## 🌍 官方网站 https://blockatm.net/

|                                    |                                    |
| ---------------------------------- | ---------------------------------- |
| ![Demo](./.github/imgs/intro1.png) | ![Demo](./.github/imgs/intro2.png) |
| ![Demo](./.github/imgs/intro3.png) | ![Demo](./.github/imgs/intro4.png) |





# BlockATM SDK 🚀
**为区块链开发者提供的一站式API接入工具，快速集成至您的DApp或交易所**  
👉 [立即体验Demo](https://blockatm.net/) | 📖 [完整文档](https://github.com/BlockATMOnLine/blockatm-sdk/tree/master/docs) | 🐞 [提交问题](https://github.com/BlockATMOnLine/blockatm-sdk/issues)

![BlockATM SDK示例截图](https://via.placeholder.com/800x400.png?text=SDK+Demo+Preview)  
*（建议替换为实际项目截图或功能流程图）*

---

## 目录
- [核心功能](#核心功能)
- [快速开始](#快速开始)
- [安装与配置](#安装与配置)
- [使用示例](#使用示例)
- [API参考](#api参考)
- [贡献指南](#贡献指南)
- [许可证](#许可证)
- [联系我们](#联系我们)

---

## 核心功能
- **多链支持**：兼容以太坊、BSC、Polygon等主流公链，一键切换网络。
- **安全交易**：内置智能合约审计模块，支持离线签名与硬件钱包集成。
- **实时数据**：提供区块链浏览器级数据接口（余额查询、交易记录、Gas价格预测）。
- **开发者友好**：支持TypeScript/JavaScript，提供完整的类型声明和测试用例。

---

## 快速开始
### 前置条件
- Node.js ≥ 16.x
- npm/yarn

### 安装与配置
```bash
# 安装SDK
npm install @blockatm/sdk
# 或使用yarn
yarn add @blockatm/sdk
```

### 初始化示例
```javascript
import BlockATM from '@blockatm/sdk';

const sdk = new BlockATM({
  apiKey: 'YOUR_API_KEY', // 从BlockATM官网获取
  network: 'mainnet',     // 支持testnet/mainnet
});

// 查询以太坊地址余额
const balance = await sdk.getBalance('0x...');
console.log('余额:', balance);
```

---

## 使用示例
### 场景1：发起一笔ERC20转账
```javascript
const txHash = await sdk.transferERC20({
  from: '0x...',
  to: '0x...',
  amount: '100',
  contractAddress: '0x...', // ERC20合约地址
  privateKey: '0x...',      // 或使用钱包连接
});
console.log('交易哈希:', txHash);
```

### 场景2：监听链上事件
```javascript
sdk.subscribe('block', (block) => {
  console.log('新区块:', block.number);
});
```

---

## API参考
完整API文档请查看 [API文档](https://github.com/BlockATMOnLine/blockatm-sdk/wiki/API-Reference)，包含：
- 账户管理
- 交易构建与签名
- 智能合约交互
- 数据查询接口

---

## 贡献指南
我们欢迎所有形式的贡献！请遵循以下步骤：
1. Fork仓库并创建分支：`git checkout -b feature/your-feature`
2. 提交代码并遵循[代码规范](https://github.com/BlockATMOnLine/blockatm-sdk/blob/master/CONTRIBUTING.md#代码规范)
3. 发起Pull Request，并关联相关Issue

---

## 许可证
本项目采用 **MIT许可证**，详情请参阅 [LICENSE](https://github.com/BlockATMOnLine/blockatm-sdk/blob/master/LICENSE)。

---

## 联系我们
- 📧 Telegram：https://t.me/Carlos_MTF
- 💬 Discord社区：[加入讨论](https://discord.gg/blockatm)
- 🌐 官网：[https://blockatm.net](https://blockatm.net)
