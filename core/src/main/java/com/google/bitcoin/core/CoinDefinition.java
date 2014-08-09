package com.google.bitcoin.core;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Hash Engineering Solutions
 * Date: 5/3/14
 * To change this template use File | Settings | File Templates.
 */
public class CoinDefinition {


    public static final String coinName = "MagCoin";
    public static final String coinTicker = "MAG";
    public static final String coinURIScheme = "magcoin";
    public static final String cryptsyMarketId = "155";
    public static final String cryptsyMarketCurrency = "BTC";
    public static final String PATTERN_PRIVATE_KEY_START = "[7X]";

    public enum CoinPrecision {
        Coins,
        Millicoins,
    }
    public static final CoinPrecision coinPrecision = CoinPrecision.Coins;


    public static final String BLOCKEXPLORER_BASE_URL_PROD = "http://blocks.magcoin.info/";    
    public static final String BLOCKEXPLORER_ADDRESS_PATH = "address/";             
    public static final String BLOCKEXPLORER_TRANSACTION_PATH = "tx/";              
    public static final String BLOCKEXPLORER_BLOCK_PATH = "block/";                 
    public static final String BLOCKEXPLORER_BASE_URL_TEST = BLOCKEXPLORER_BASE_URL_PROD;

    public static final String DONATION_ADDRESS = "mHkSBvZHxSBV98RsCfBxpjLZF2MxdiC7z1";  
    public static final String DONATION_ADDRESS_TESTNET = "";

    enum CoinHash {
        SHA256,
        scrypt,
        x11
    };

    public static final CoinHash coinPOWHash = CoinHash.x11;

    public static boolean checkpointFileSupport = true;

    public static final int TARGET_TIMESPAN = (int)(24 * 60 * 60);  // 24 hours per difficulty cycle, on average.
    public static final int TARGET_SPACING = (int)(3 * 60);  // 3 minutes seconds per block.
    public static final int INTERVAL = TARGET_TIMESPAN / TARGET_SPACING;  //36 blocks

    public static final int getInterval(int height, boolean testNet) {
            return INTERVAL;      //108
    }
    public static final int getIntervalCheckpoints() {
            return INTERVAL;

    }
    public static final int getTargetTimespan(int height, boolean testNet) {
            return TARGET_TIMESPAN;    //72 min
    }

    public static int spendableCoinbaseDepth = 100; //main.h: static const int COINBASE_MATURITY
    public static final BigInteger MAX_MONEY = BigInteger.valueOf(250000000).multiply(Utils.COIN);                 //main.h:  MAX_MONEY
    //public static final String MAX_MONEY_STRING = "20000000000";     //main.h:  MAX_MONEY

    public static final BigInteger DEFAULT_MIN_TX_FEE = BigInteger.valueOf(1000000);   // MIN_TX_FEE
    public static final BigInteger DUST_LIMIT = BigInteger.valueOf(1000); //main.h CTransaction::GetMinFee        0.01 coins

    public static final int PROTOCOL_VERSION = 60001;          //version.h PROTOCOL_VERSION
    public static final int MIN_PROTOCOL_VERSION = 60001;        //version.h MIN_PROTO_VERSION
    public static final int BIP0031_VERSION = 60000;

    public static final int BLOCK_CURRENTVERSION = 1;   //CBlock::CURRENT_VERSION
    public static final int MAX_BLOCK_SIZE = 1 * 1000 * 1000;


    public static final boolean supportsBloomFiltering = false; //Requires PROTOCOL_VERSION 70000 in the client

    public static final int Port    = 22557;       //protocol.h GetDefaultPort(testnet=false)
    public static final int TestPort = 32556;     //protocol.h GetDefaultPort(testnet=true)

    //
    //  Production
    //
    public static final int AddressHeader = 110;             //base58.h CBitcoinAddress::PUBKEY_ADDRESS
    public static final int p2shHeader = 5;             //base58.h CBitcoinAddress::SCRIPT_ADDRESS
    public static final boolean allowBitcoinPrivateKey = false; //for backward compatibility with previous version of digitalcoin
    public static final int dumpedPrivateKeyHeader = 128;   //common to all coins
    public static final long oldPacketMagic = 0xd3b7c0f4;      //0xd3, 0xb7, 0xc0, 0xf4
    public static final long PacketMagic = 0xd3b7c0f4;

    //Genesis Block Information from main.cpp: LoadBlockIndex
    static public long genesisBlockDifficultyTarget = (0x1e0ffff0L);         //main.cpp: LoadBlockIndex
    static public long genesisBlockTime = 1407522600L;                       //main.cpp: LoadBlockIndex
    static public long genesisBlockNonce = (1022937);                         //main.cpp: LoadBlockIndex
    static public String genesisHash = "0000022011f0e64f9a32b8c344ef28df2bbf30183867060a5ebde875dc5b159f"; //main.cpp: hashGenesisBlock STOPPED HERE
    static public String genesisMerkleRoot = "6c738a0a1ad25cfa294990b9558138af8bc7932554932be5e3adac949337865e";
    static public int genesisBlockValue = 50;                                                              //main.cpp: LoadBlockIndex
    //taken from the raw data of the block explorer
    static public String genesisTxInBytes = "04ffff001d010426416d65726963616e2045626f6c612050617469656e7420426567696e73205265636f76657279";
    static public String genesisTxOutBytes = "0";

    //net.cpp strDNSSeed
    static public String[] dnsSeeds = new String[] {""};

    public static int minBroadcastConnections = 0;   //0 for default; we need more peers.

    //
    // TestNet - dimecoin - not tested
    //
    public static final boolean supportsTestNet = true;
    public static final int testnetAddressHeader = 111;             //base58.h CBitcoinAddress::PUBKEY_ADDRESS_TEST
    public static final int testnetp2shHeader = 196;             //base58.h CBitcoinAddress::SCRIPT_ADDRESS_TEST
    public static final long testnetPacketMagic = 0xfbc0b8db;      //
    public static final String testnetGenesisHash = "00000a65cd6f26d8c623ecdc04cdbd081153a32b2169994e8e0bc65a7284c23d";
    static public long testnetGenesisBlockDifficultyTarget = (0x1e0ffff0L);         //main.cpp: LoadBlockIndex
    static public long testnetGenesisBlockTime = 1407270000L;                       //main.cpp: LoadBlockIndex
    static public long testnetGenesisBlockNonce = (23466L);                         //main.cpp: LoadBlockIndex





    //main.cpp GetBlockValue(height, fee)
    public static final BigInteger GetBlockReward(int height, double difficulty)
    {
        int COIN = 1;
	   if(difficulty < 1) { difficulty = 1; }
	   
        int tempSub = 20;
        tempSub = tempSub / (97200 + height);
        tempSub = tempSub * 97200;
        BigInteger nSubsidy = Utils.toNanoCoins(tempSub, 0);
	   nSubsidy.shiftRight(height / 30000);
        return nSubsidy;
    }

    public static int subsidyDecreaseBlockCount = 30000;     //main.cpp GetBlockValue(height, fee)

    public static BigInteger proofOfWorkLimit = Utils.decodeCompactBits(0x1e0fffffL);  //main.cpp bnProofOfWorkLimit (~uint256(0) >> 20); // digitalcoin: starting difficulty is 1 / 2^12

    static public String[] testnetDnsSeeds = new String[] {""};
    //from main.h: CAlert::CheckSignature
    public static final String SATOSHI_KEY = "040184710fa689ad5023690c80f3a49c8f13f8d45b8c857fbcbc8bc4a8e4d3eb4b10f4d4604fa08dce601aaf0f470216fe1b51850b4acf21b179c45070ac7b03a9";
    public static final String TESTNET_SATOSHI_KEY = "040184710fa689ad5023690c80f3a49c8f13f8d45b8c857fbcbc8bc4a8e4d3eb4b10f4d4604fa08dce601aaf0f470216fe1b51850b4acf21b179c45070ac7b03a9";

    /** The string returned by getId() for the main, production network where people trade things. */
    public static final String ID_MAINNET = "org.magcoin.production";
    /** The string returned by getId() for the testnet. */
    public static final String ID_TESTNET = "org.magcoin.test";
    /** Unit test network. */
    public static final String ID_UNITTESTNET = "com.google.magcoin.unittest";

    //checkpoints.cpp Checkpoints::mapCheckpoints
    public static void initCheckpoints(Map<Integer, Sha256Hash> checkpoints)
    {

        checkpoints.put(  0, new Sha256Hash("00000a65cd6f26d8c623ecdc04cdbd081153a32b2169994e8e0bc65a7284c23d"));
    }

    //Unit Test Information
    public static final String UNITTEST_ADDRESS = "mL4oJPEPCxnP6xeqaYH8xw6PuX4JiZvPKp";
    public static final String UNITTEST_ADDRESS_PRIVATE_KEY = "cH1JkH1qGEmQ28ZdzR7w774HNXC3Q83uZXzjwLDnr2HVVc7yzTkK";

}
