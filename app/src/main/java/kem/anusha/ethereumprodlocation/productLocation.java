package kem.anusha.ethereumprodlocation;

/**
 * Created by Guest1 on 9/5/2017.
 */
import org.web3j.abi.datatypes.Int;
import org.web3j.abi.datatypes.NumericType;
import org.web3j.abi.datatypes.Uint;
import org.web3j.tx.Contract;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import org.web3j.tx.TransactionManager;
import org.web3j.utils.Numeric;

public class productLocation extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b5b33600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600060018190555060006002819055505b5b61085d806100726000396000f3006060604052361561008c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806302d05d3f1461009157806383197ef0146100e6578063a4da651e146100fb578063a87d942c14610132578063b16dbd211461015b578063b734605714610184578063c98ce30114610297578063e5aa3d5814610340575b600080fd5b341561009c57600080fd5b6100a4610369565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156100f157600080fd5b6100f961038f565b005b341561010657600080fd5b61011c6004808035906020019091905050610423565b6040518082815260200191505060405180910390f35b341561013d57600080fd5b610145610448565b6040518082815260200191505060405180910390f35b341561016657600080fd5b61016e610453565b6040518082815260200191505060405180910390f35b341561018f57600080fd5b6101ae6004808035906020019091908035906020019091905050610459565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b838110156101f35780820151818401525b6020810190506101d7565b50505050905090810190601f1680156102205780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b8381101561025a5780820151818401525b60208101905061023e565b50505050905090810190601f1680156102875780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34156102a257600080fd5b61033e600480803590602001909190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610615565b005b341561034b57600080fd5b6103536106b9565b6040518082815260200191505060405180910390f35b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141561042057600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16ff5b5b565b6000806000808481526020019081526020016000208054905090508091505b50919050565b600060015490505b90565b60015481565b6104616106bf565b6104696106bf565b6000808581526020019081526020016000208381548110151561048857fe5b906000526020600020906002020160005b50600001600080868152602001908152602001600020848154811015156104bc57fe5b906000526020600020906002020160005b50600101818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105665780601f1061053b57610100808354040283529160200191610566565b820191906000526020600020905b81548152906001019060200180831161054957829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106025780601f106105d757610100808354040283529160200191610602565b820191906000526020600020905b8154815290600101906020018083116105e557829003601f168201915b50505050509050915091505b9250929050565b600080848152602001908152602001600020805480600101828161063991906106d3565b916000526020600020906002020160005b60408051908101604052808681526020018581525090919091506000820151816000019080519060200190610680929190610705565b50602082015181600101908051906020019061069d929190610705565b505050506001600081548092919060010191905055505b505050565b60025481565b602060405190810160405280600081525090565b815481835581811511610700576002028160020283600052602060002091820191016106ff9190610785565b5b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061074657805160ff1916838001178555610774565b82800160010185558215610774579182015b82811115610773578251825591602001919060010190610758565b5b50905061078191906107c4565b5090565b6107c191905b808211156107bd57600080820160006107a491906107e9565b6001820160006107b491906107e9565b5060020161078b565b5090565b90565b6107e691905b808211156107e25760008160009055506001016107ca565b5090565b90565b50805460018160011615610100020316600290046000825580601f1061080f575061082e565b601f01602090049060005260206000209081019061082d91906107c4565b5b505600a165627a7a72305820f42e45a0d428f188e10aa75c86bf6967296add6ef2e6e39ed1367ca178de27810029";

    private productLocation(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private productLocation(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public Future<TransactionReceipt> kill() {
        Function function = new Function("kill", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Int> getrfIdCount(Utf8String rfID) {
        Function function = new Function("getrfIdCount",
                Arrays.<Type>asList(rfID),
                Arrays.<TypeReference<?>>asList(new TypeReference<Int>() {}));
        return executeCallSingleValueReturnAsync(function);
    }
    public Future<Int> prodCnt() {
        Function function = new Function("prodCnt",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Int>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Utf8String> getProductDetails(Utf8String rfID, Uint b) {
        Function function = new Function("getProductDetails",
                Arrays.<Type>asList(rfID, b),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));

        return executeCallSingleValueReturnAsync(function);
    }
    public Future<Utf8String> getProductName(Utf8String rfID) {
        Function function = new Function("getProductName",
                Arrays.<Type>asList(rfID),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));

        return executeCallSingleValueReturnAsync(function);
    }



    public Future<TransactionReceipt> addProdInfo(Utf8String rfID,Utf8String pName,Utf8String pLocation) {
        Function function = new Function("addProdInfo", Arrays.<Type>asList(rfID,pName,pLocation), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public static Future<productLocation> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Utf8String _greeting) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_greeting));
        return deployAsync(productLocation.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static Future<productLocation> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Utf8String _greeting) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_greeting));
        return deployAsync(productLocation.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static productLocation load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new productLocation(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static productLocation load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new productLocation(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
