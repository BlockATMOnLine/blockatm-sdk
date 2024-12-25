# BlockATM SmartContract SDK
<p>
<a href="https://www.gnu.org/licenses/gpl-3.0.html"><img src="https://img.shields.io/badge/license-GPLV3-blue" alt="license GPLV3"></a>
<a href="https://github.com/assimon/dujiaoka/releases/tag/1.0.0"><img src="https://img.shields.io/badge/version-1.0.0-red" alt="version 1.0.0"></a>
</p>
<h2 align="center">繁體中文 | <a href="README.md">English</a></h2>  

## BlockATM - `區塊鏈`支付解決方案

>一款開源免費的，為商戶提供加密貨幣`代收代付`功能的支付解決方案，支持自定義收款錢包地址以及收款通知，支持代付API對接。可以收取`USDT`、`USDC`、`DAI`、`TUSD`等各類幣種。


## AD -- PassTo-多種資產抵押信用卡
>無上限信用額 單筆消費可達$5,000,000  
信用額度按照抵押品價格而設定，支持：證券、物業、貴金屬、數字資產等  
可绑微信、支付宝、美区AppStore消费  
[點擊領取你的國際信用卡](https://passtocredit.io/)

## AD -- BlockATM-智能合約收付款解決方案
>完全去中心化的商戶收付款方式  
使用智能合約進行資金的安全管理  
使用費率比fireBlocks和metaMask都低  
[點擊創建你的智能合約櫃檯](https://www.blockatm.net/)

## 項目簡介
-  封装合约交互，帮助客户完自动资产代付
-  支持自定义RPC节点配置
-  支持链上其他基础交互
-  遵守 [GPLv3](https://www.gnu.org/licenses/gpl-3.0.html) 開源協議

## 項目結構
```
project
blockatm-sdk                
  ├── blockatm-sdk-core         # sdk核心工具包
  ├  ├  main
  ├    ├── eth                  # eth网络合约交互工具接口
  ├    ├── tron                 # tron网络合约交互工具接口
  ├  ├── test                   # 单元测试
  ├    ├── PayoutHelper         # eth网络单元测试
  ├    ├── TronHelper           # tron网络单元测试
  ├── tron-core                 # tron底层方法封装
  ├── tron-protobuf             # tron底层协议对象定义
  
```
#### ETH主要功能
- 查询代币余额
- 创建未签名代付交易
- 交易签名
- 交易广播
- 查询交易状态
- 查询当前gasPrice
- 设置链上rpc节点
- 设置代付合约地址

#### TRON主要功能
- 查询代币余额
- 创建未签名代付交易
- 交易签名
- 交易广播
- 查询交易状态
- 设置链上rpc节点

### 使用說明 [详细文档/技术支持](https://t.me/PayCool_John)
- 1. 通过[BlockATM](https://www.blockatm.net/)注册并部署属于自己的付款合约

![Imgur](https://i.imgur.com/MuBCm8e.png)

- 2. 在您的代码中通过maven引入sdk jar包([最新版本地址](https://central.sonatype.com/artifact/io.github.BlockATMOnLine.sdk/blockatm-sdk-core))
```
 <dependency>
      <groupId>io.github.BlockATMOnLine.sdk</groupId>
      <artifactId>blockatm-sdk-core</artifactId>
      <version>1.0.2</version>
 </dependency>
```

- 3. 代码使用示例
```java
PayoutHelper payout = new PayoutHelper("rpc_url");
Boolean result = payout.txIsSuccessful("your_tx_id");
```

  

## 加入交流/意見反饋
- Telegram：https://t.me/Carlos_MTF
- Email：alex.t@chixi88.com



