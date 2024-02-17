package com.zjavax.cardanoproject;

import com.bloxbean.cardano.client.api.exception.ApiException;
import com.bloxbean.cardano.client.api.model.Amount;
import com.bloxbean.cardano.client.api.model.Result;
import com.bloxbean.cardano.client.api.model.Utxo;
import com.bloxbean.cardano.client.backend.model.Asset;
import com.bloxbean.cardano.client.common.CardanoConstants;
import com.bloxbean.cardano.client.exception.CborSerializationException;
import com.bloxbean.cardano.client.quicktx.QuickTxBuilder;
import com.bloxbean.cardano.client.quicktx.ScriptTx;
import com.bloxbean.cardano.client.transaction.spec.Transaction;
import com.bloxbean.cardano.client.util.JsonUtil;
import com.zjavax.cardanoproject.entity.ReceiverData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/getTxWithoutSign")
    public String getTxWithoutSign(List<String> utxoStrList, ReceiverData receiverData) {
        List<Utxo> utxoList = new ArrayList<>();
        for(String utxoStr:utxoStrList) {
            Utxo utxo = getUtxoByhashAndOutputIndex(utxoStr);
            utxoList.add(utxo);
        }

        List<Amount> amountList = new ArrayList<>();
        for(Asset asset:receiverData.getAssetList()){
            if(CardanoConstants.LOVELACE.equals(asset.getAssetName())) {
                amountList.add(Amount.ada(Double.valueOf(asset.getQuantity())));
            } else {
                amountList.add(Amount.asset(asset.getPolicyId(), asset.getAssetName(), BigInteger.valueOf(Long.parseLong(asset.getQuantity()))));
            }

        }

        ScriptTx scriptTx = new ScriptTx()
                .payToAddress(receiverData.getReceiverAddress(), amountList)
                .withChangeAddress("addr_test1qpszcr5nke788gwujpd3kceq9v7ucq7nax6amnl54enplslq3hh6n644cphm3cktvtjusv89m57segsdljcx3jekv5wscn6r4t")
                .collectFrom(utxoList)
                ;

        QuickTxBuilder quickTxBuilder = new QuickTxBuilder(getBackendService());
        Transaction txWithoutSign = quickTxBuilder.compose(scriptTx)
                .feePayer(receiverData.getFeePayerAddress())
                .additionalSignersCount(receiverData.getSignersCount())
                .validTo(getSlot())
//                .mergeOutputs(false)
                .build();

        System.out.println(JsonUtil.getPrettyJson(txWithoutSign));

        try {
            return txWithoutSign.serializeToHex();
        } catch (CborSerializationException e) {
            throw new RuntimeException(e);
        }
    }




}
