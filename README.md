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
src
  ├── main 
  ├  ├── eth ---> (eth相关实现)
  ├  ├── tron ---> (tron相关实现)
  ├── test ---> (单元测试)
  
```


### 使用說明

- 下载源码到本地
- 部署到本地仓库 `maven install` 
- 引入依赖包
```
<dependency>
    <groupId>com.block.atm.sdk</groupId>
    <artifactId>blockatm-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

### ETH主要功能
- 查询代币余额
- 创建未签名代付交易
- 交易签名
- 交易广播
- 查询交易状态
- 查询当前gasPrice
- 设置链上rpc节点
- 设置代付合约地址

### TRON主要功能
- 查询代币余额
- 创建未签名代付交易
- 交易签名
- 交易广播
- 查询交易状态
- 设置链上rpc节点



## 設計實現
通过 web3j封装与合约交互


## 加入交流/意見反饋
- Telegram：https://t.me/PayCool_Erik
- Email：erik.wang@chixi88.com



