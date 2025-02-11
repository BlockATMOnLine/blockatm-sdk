<div align="center">

<a href="https://blockatm.net/"><img src="https://blockatm.net/assets/images/resources/logo-dark.png" width="477" height="101" alt="BlockATM Logo"></a>

# BlockATM
## 永不触碰您的资金，100%安全</br></br>

<p align="center">
  <a href="./README_en.md">English</a> |
  <a href="./README_cn.md">简体中文</a> |
  <a href="./README_ja.md">繁體中文</a>
</p>

</div>

</br>

## 🌍 官方网站 https://blockatm.net/

|                                    |                                    |
| ---------------------------------- | ---------------------------------- |
| ![Dashboard](https://github.com/BlockATMOnLine/blockatm-sdk/blob/master/blockATM%20snapshot/blockatm%20dashboard.png) | ![Asset](https://github.com/BlockATMOnLine/blockatm-sdk/blob/master/blockATM%20snapshot/blockatm%20assets.png) |
| ![Payment](https://github.com/BlockATMOnLine/blockatm-sdk/blob/master/blockATM%20snapshot/blockatm%20payment.png) | ![Settings](https://github.com/BlockATMOnLine/blockatm-sdk/blob/master/blockATM%20snapshot/blockatm%20settings.png) |

</br>
## BlockATM是什么？

- **完全去中心化的支付解决方案**，利用智能合约提供安全、便捷且可信赖的加密货币支付服务。
- **适用于Web3生态的支付基础设施**，可用于NFT交易、去中心化金融（DeFi）等领域。  
- **智能合约自托管平台**，让用户100%掌控资金，无需依赖中心化机构。  
- **低成本交易**，每笔交易仅收取2 USDT，无额外平台费用。  
- **快速集成的支付工具**，支持一键嵌入小部件代码，轻松接入任意网站。  
- **实时交易通知系统**，通过Webhook推送交易状态，确保商户和用户即时获取信息。  
- **智能化交易防错**，客户无需手动输入钱包地址，避免输入错误导致的资产损失。  
- **全球化的跨境支付**，支持跨境交易，实现加密货币的无国界支付。  
- **BlockATM 是一个无信任依赖的金融工具**，所有交易均由智能合约执行，减少人为干预风险。  
- **BlockATM 是一个全天候运作的支付系统**，不受银行时间限制，24/7全天候交易处理。  
- **数据透明公开**，所有交易记录均可在区块链上查询，确保数据真实可验证。  
- **简化支付流程**，无需传统账户体系，即可进行加密货币交易。  
- **企业级支付工具**，适用于工资发放、供应链支付等场景。  
- **灵活的支付API**，可为开发者提供可扩展的支付接口，支持定制化集成。  

</br>
# BlockATM SDK 🚀
**为区块链开发者提供的一站式API接入工具，快速集成至您的DApp或交易所**  

👉 [立即体验](https://blockatm.net/) 
📖 [完整文档](https://blockatm.gitbook.io/blockatm) 
🐞 [提交问题](https://github.com/BlockATMOnLine/blockatm-sdk/issues)


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
