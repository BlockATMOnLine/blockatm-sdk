package com.block.atm.sdk.tron.core;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author Lawrence
 * @description
 * @date 2021-03-17 15:58
 */
@Setter
@Getter
public class TrxBlock {


    /**
     * blockID : 0000000000c9e803d5258a17e15172768b41ffb69e857530e7d9f7a92ccb78e8
     * block_header : {"raw_data":{"number":13232131,"txTrieRoot":"1b8e8977290caedf6de737f3e42d142c5c603d20d46942bb47a75963ec78709d","witness_address":"41ad3353aa1343b90a311f09b784c76f089d278232","parentHash":"0000000000c9e802e54079a6a84e92315f8b8dbcbefd9b151dc2317d27523049","version":20,"timestamp":1615967187000},"witness_signature":"aa4ed732351f8abda25ce74255393dbeff305d132d4d25d5839dffed0298be6e67e9d4672ad404f9b198854c6378b25bed3759995c249278e5c0f1d489ff910201"}
     * transactions : [{"ret":[{"contractRet":"SUCCESS"}],"signature":["51911c415dc290b1500562d41570009bbbe0827d231999ea7de1f0bbb1665b436e9a1f0c3eeff34f3df2290da5706f920e95b58a55827d7202f81fd45960a7ea01"],"txID":"738c8bdf8f2a38147a8a4643bb184cfde41b22707585669a2cfb15b3b7d869bf","raw_data":{"contract":[{"parameter":{"value":{"amount":5000000000,"owner_address":"4141f0a2b01c950b4d38c9abfe9b96579dea065168","to_address":"412e961e2d6493b2bc728d06d024c0e2a631063538"},"type_url":"type.googleapis.com/protocol.TransferContract"},"type":"TransferContract"}],"ref_block_bytes":"e7f0","ref_block_hash":"85fc9d795829e089","expiration":1615967244000,"timestamp":1615967184487},"raw_data_hex":"0a02e7f0220885fc9d795829e08940e09d9df9832f5a69080112650a2d747970652e676f6f676c65617069732e636f6d2f70726f746f636f6c2e5472616e73666572436f6e747261637412340a154141f0a2b01c950b4d38c9abfe9b96579dea0651681215412e961e2d6493b2bc728d06d024c0e2a6310635381880e497d01270e7cc99f9832f"},{"ret":[{"contractRet":"SUCCESS"}],"signature":["b03901e89ee688c819bc885f7f4789e5f47c1eb23bdbb9ee334516f9f43e86ce2968913414cc82113c5ec6ff993a9075e23cf81474920c9b4da94ad2a096310e01"],"txID":"5d79ef636445e261fa090cdb5d23d7f8e0443778cb74df2c3ee34a9462645f67","raw_data":{"contract":[{"parameter":{"value":{"amount":5000000000,"owner_address":"4141f0a2b01c950b4d38c9abfe9b96579dea065168","to_address":"4131ee9cedde5c5e0de33f26b3ba6596b0fef610f3"},"type_url":"type.googleapis.com/protocol.TransferContract"},"type":"TransferContract"}],"ref_block_bytes":"e7f0","ref_block_hash":"85fc9d795829e089","expiration":1615967244000,"timestamp":1615967184529},"raw_data_hex":"0a02e7f0220885fc9d795829e08940e09d9df9832f5a69080112650a2d747970652e676f6f676c65617069732e636f6d2f70726f746f636f6c2e5472616e73666572436f6e747261637412340a154141f0a2b01c950b4d38c9abfe9b96579dea06516812154131ee9cedde5c5e0de33f26b3ba6596b0fef610f31880e497d0127091cd99f9832f"},{"ret":[{"contractRet":"SUCCESS"}],"signature":["542dc9f8a72fd3d96a03e4075789cdd017f9c7a30522fffbd1c4a2def34dfcbf0ccdc3302507168c4cc41715d778ce3f452b988840c23f80cd4985fbc75f733a01"],"txID":"ec0fa0d734c1d5815cee80b7accce61b0cda8a87e8d3e40061595104a4142430","raw_data":{"data":"73656e6420746f20746f6e79","contract":[{"parameter":{"value":{"data":"a9059cbb00000000000000000000000001c12a552c8a35051153c05792249d2512f011ba000000000000000000000000000000000000000000000000112209c76de80000","owner_address":"41bcaadebd7d6951971e4876f79fd95351bd5571dc","contract_address":"4135cf7d6a20a1f7149e5f99442334257a95e8f28e"},"type_url":"type.googleapis.com/protocol.TriggerSmartContract"},"type":"TriggerSmartContract"}],"ref_block_bytes":"e7ef","ref_block_hash":"ca589e3ac0ae6383","expiration":1615967241000,"fee_limit":6000000,"timestamp":1615967184149},"raw_data_hex":"0a02e7ef2208ca589e3ac0ae638340a8869df9832f520c73656e6420746f20746f6e795aae01081f12a9010a31747970652e676f6f676c65617069732e636f6d2f70726f746f636f6c2e54726967676572536d617274436f6e747261637412740a1541bcaadebd7d6951971e4876f79fd95351bd5571dc12154135cf7d6a20a1f7149e5f99442334257a95e8f28e2244a9059cbb00000000000000000000000001c12a552c8a35051153c05792249d2512f011ba000000000000000000000000000000000000000000000000112209c76de800007095ca99f9832f9001809bee02"}]
     */

    private String blockID;
    private BlockHeaderBean block_header;
    private List<TransactionsBean> transactions;

    @Setter
    @Getter
    public static class BlockHeaderBean {
        /**
         * raw_data : {"number":13232131,"txTrieRoot":"1b8e8977290caedf6de737f3e42d142c5c603d20d46942bb47a75963ec78709d","witness_address":"41ad3353aa1343b90a311f09b784c76f089d278232","parentHash":"0000000000c9e802e54079a6a84e92315f8b8dbcbefd9b151dc2317d27523049","version":20,"timestamp":1615967187000}
         * witness_signature : aa4ed732351f8abda25ce74255393dbeff305d132d4d25d5839dffed0298be6e67e9d4672ad404f9b198854c6378b25bed3759995c249278e5c0f1d489ff910201
         */

        private RawDataBean raw_data;
        private String witness_signature;

        @Setter
        @Getter
        public static class RawDataBean {
            /**
             * number : 13232131
             * txTrieRoot : 1b8e8977290caedf6de737f3e42d142c5c603d20d46942bb47a75963ec78709d
             * witness_address : 41ad3353aa1343b90a311f09b784c76f089d278232
             * parentHash : 0000000000c9e802e54079a6a84e92315f8b8dbcbefd9b151dc2317d27523049
             * version : 20
             * timestamp : 1615967187000
             */

            private BigInteger number;
            private String txTrieRoot;
            private String witness_address;
            private String parentHash;
            private int version;
            private long timestamp;

        }
    }

    @Setter
    @Getter
    public static class TransactionsBean {
        /**
         * ret : [{"contractRet":"SUCCESS"}]
         * signature : ["51911c415dc290b1500562d41570009bbbe0827d231999ea7de1f0bbb1665b436e9a1f0c3eeff34f3df2290da5706f920e95b58a55827d7202f81fd45960a7ea01"]
         * txID : 738c8bdf8f2a38147a8a4643bb184cfde41b22707585669a2cfb15b3b7d869bf
         * raw_data : {"contract":[{"parameter":{"value":{"amount":5000000000,"owner_address":"4141f0a2b01c950b4d38c9abfe9b96579dea065168","to_address":"412e961e2d6493b2bc728d06d024c0e2a631063538"},"type_url":"type.googleapis.com/protocol.TransferContract"},"type":"TransferContract"}],"ref_block_bytes":"e7f0","ref_block_hash":"85fc9d795829e089","expiration":1615967244000,"timestamp":1615967184487}
         * raw_data_hex : 0a02e7f0220885fc9d795829e08940e09d9df9832f5a69080112650a2d747970652e676f6f676c65617069732e636f6d2f70726f746f636f6c2e5472616e73666572436f6e747261637412340a154141f0a2b01c950b4d38c9abfe9b96579dea0651681215412e961e2d6493b2bc728d06d024c0e2a6310635381880e497d01270e7cc99f9832f
         */

        private String txID;
        private RawDataBeanX raw_data;
        private String raw_data_hex;
        private List<RetBean> ret;
        private List<String> signature;

        @Setter
        @Getter
        public static class RawDataBeanX {
            /**
             * contract : [{"parameter":{"value":{"amount":5000000000,"owner_address":"4141f0a2b01c950b4d38c9abfe9b96579dea065168","to_address":"412e961e2d6493b2bc728d06d024c0e2a631063538"},"type_url":"type.googleapis.com/protocol.TransferContract"},"type":"TransferContract"}]
             * ref_block_bytes : e7f0
             * ref_block_hash : 85fc9d795829e089
             * expiration : 1615967244000
             * timestamp : 1615967184487
             */

            private String ref_block_bytes;
            private String ref_block_hash;
            private long expiration;
            private long timestamp;
            private List<ContractBean> contract;

            @Setter
            @Getter
            public static class ContractBean {
                /**
                 * parameter : {"value":{"amount":5000000000,"owner_address":"4141f0a2b01c950b4d38c9abfe9b96579dea065168","to_address":"412e961e2d6493b2bc728d06d024c0e2a631063538"},"type_url":"type.googleapis.com/protocol.TransferContract"}
                 * type : TransferContract
                 */

                private ParameterBean parameter;
                private String type;

                @Setter
                @Getter
                public static class ParameterBean {
                    /**
                     * value : {"amount":5000000000,"owner_address":"4141f0a2b01c950b4d38c9abfe9b96579dea065168","to_address":"412e961e2d6493b2bc728d06d024c0e2a631063538"}
                     * type_url : type.googleapis.com/protocol.TransferContract
                     */

                    private ValueBean value;
                    private String type_url;

                    @Setter
                    @Getter
                    public static class ValueBean {
                        /**
                         * amount : 5000000000
                         * owner_address : 4141f0a2b01c950b4d38c9abfe9b96579dea065168
                         * to_address : 412e961e2d6493b2bc728d06d024c0e2a631063538
                         */

                        private Long amount;
                        private String owner_address;
                        private String contract_address;
                        private String to_address;
                        private String data;
                        private BigDecimal call_value;

                    }
                }
            }
        }

        @Setter
        @Getter
        public static class RetBean {
            /**
             * contractRet : SUCCESS
             */

            private String contractRet;

        }
    }
}
