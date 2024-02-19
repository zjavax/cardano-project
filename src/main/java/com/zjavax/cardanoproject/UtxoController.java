package com.zjavax.cardanoproject;

import com.bloxbean.cardano.client.address.Address;
import com.bloxbean.cardano.client.address.AddressProvider;
import com.bloxbean.cardano.client.api.exception.ApiException;
import com.bloxbean.cardano.client.api.model.Amount;
import com.bloxbean.cardano.client.api.model.Result;
import com.bloxbean.cardano.client.api.model.Utxo;
import com.bloxbean.cardano.client.backend.api.AccountService;
import com.bloxbean.cardano.client.backend.model.AccountInformation;
import com.bloxbean.cardano.client.backend.model.Asset;
import com.bloxbean.cardano.client.common.CardanoConstants;
import com.bloxbean.cardano.client.exception.CborSerializationException;
import com.bloxbean.cardano.client.quicktx.QuickTxBuilder;
import com.bloxbean.cardano.client.quicktx.ScriptTx;
import com.bloxbean.cardano.client.quicktx.Tx;
import com.bloxbean.cardano.client.transaction.spec.Transaction;
import com.bloxbean.cardano.client.util.JsonUtil;
import com.zjavax.cardanoproject.entity.CollectFromUtxo;
import com.zjavax.cardanoproject.entity.ReceiverData;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController()
@RequestMapping("/utxo")
public class UtxoController extends QuickTxBaseIT {

    @GetMapping("/getUtxoByAddress")
    public Result<List<Utxo>> getUtxoByAddress(@RequestParam String address, @RequestParam int count, @RequestParam int page) throws ApiException {
        return getBackendService().getUtxoService().getUtxos(address, count, page);
    }

    /**
     * 多发送方（utxo），一个接收方
     * @param utxoStrList
     * @param receiverAddress
     * @param feePayerAddress
     * @param signersCount
     * @param assetList
     * @return
     */
    @PostMapping("/getTxWithoutSign")
    public String getTxWithoutSign(@RequestParam List<String> utxoStrList, @RequestBody ReceiverData receiverData) throws ApiException, CborSerializationException {
        List<Utxo> utxoList = new ArrayList<>();
        for(String utxoStr: utxoStrList) {
            Utxo utxo = getUtxoByhashAndOutputIndex(utxoStr);
            utxoList.add(utxo);
        }

        List<Amount> amountList = new ArrayList<>();
        for(Asset asset: receiverData.getAssetList()){
            if(Constant.ADA.equals(asset.getAssetName())) {
                amountList.add(Amount.ada(Double.valueOf(asset.getQuantity())));
            } else {
                amountList.add(Amount.asset(asset.getPolicyId(), asset.getAssetName(), BigInteger.valueOf(Long.parseLong(asset.getQuantity()))));
            }

        }

        Tx tx = new Tx()
                .payToAddress(receiverData.getReceiverAddress(), amountList)
                .collectFrom(utxoList)
                .from(utxoList.get(0).getAddress())
                ;

        if(receiverData.getStakeAddressList() !=null){
            for (String stakeAddress:receiverData.getStakeAddressList()){
                if(stakeAddress.startsWith("addr")) {
                    Address address =  new Address(stakeAddress);
                    stakeAddress = AddressProvider.getStakeAddress(address).getAddress();
                }
                AccountService accountService = getBackendService().getAccountService();
                AccountInformation account = accountService.getAccountInformation(stakeAddress).getValue();

                tx = tx.withdraw(stakeAddress, BigInteger.valueOf(Long.parseLong(account.getWithdrawableAmount())));
            }

        }

        QuickTxBuilder quickTxBuilder = new QuickTxBuilder(getBackendService());
        Transaction txWithoutSign = quickTxBuilder.compose(tx)
                .additionalSignersCount(receiverData.getSignersCount())
                .validTo(getSlot())
                .build();

        System.out.println(JsonUtil.getPrettyJson(txWithoutSign));

        return txWithoutSign.serializeToHex();
    }




}
