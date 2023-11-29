
# Blockatm-sdk

## 简介：

通过Blockatm-sdk，它可以帮助你快速地连接代付智能合约实现出金。

## 使用步骤

### maven

```
<dependency>
    <groupId>com.block.atm.sdk</groupId>
    <artifactId>blockatm-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 使用示例


查询代币余额

```
Erc20Helper eth = new Erc20Helper("https://eth-goerli.g.alchemy.com/v2/HwO5lIvcvSTL4PzCfFrTZwu7N__dhzkl");
BigInteger balance = eth.getBalance("0x92eFDFa35c75B259375eBe0F84ee1d95db0489b6","0x688911155d10155C77e0cDBe99dd9A7cD99dE7ff");
```
