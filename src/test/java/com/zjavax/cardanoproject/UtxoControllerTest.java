package com.zjavax.cardanoproject;

import com.bloxbean.cardano.client.api.exception.ApiException;
import com.bloxbean.cardano.client.backend.model.Asset;
import com.bloxbean.cardano.client.common.CardanoConstants;
import com.bloxbean.cardano.client.exception.CborSerializationException;
import com.zjavax.cardanoproject.entity.ReceiverData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CardanoProjectApplication.class)
public class UtxoControllerTest {

    @Autowired
    public UtxoController utxoController;

    String address = "addr1qxu7nks0vt5te3dx2wmwq5ytz7td8hsvytl2zwkrjvwm0vmy3va7sk0l7yrpe9m3s3230ynqef8p0997ddhvkpkrkuysdhwdrg";
    String receiver1 = "addr_test1qz7hv68vqcmcmkk5ag4k96pmdz3kd6lsv5x6f3h4za4533ak9zt4f5x24nmvs32w7560ku3lkht99qkeshqa3f4l5cmqvjepjm";


    @Test
    public void test() throws ApiException {
        utxoController.getUtxoByAddress(address, 100, 1);
    }


    String indigo_policyid = "fa3eff2047fdf9293c5feef4dc85ce58097ea1c6da4845a351535183";
    String assetName = "tINDY";
    @Test
    public void getTxWithoutSignTest() throws CborSerializationException, ApiException {
        List<Asset> assets = new ArrayList<>();
        Asset asset = new Asset();
        asset.setAssetName(CardanoConstants.LOVELACE);
        asset.setQuantity("3.1");
        assets.add(asset);

        Asset asset2 = new Asset();
        asset2.setPolicyId(indigo_policyid);
        asset2.setAssetName(assetName);
        asset2.setQuantity("2");
        assets.add(asset2);

        ReceiverData receiverData = ReceiverData.builder().receiverAddress(receiver1)
                .signersCount(2).assetList(assets).build();

        List<String> strings = List.of("f3897bff6f873c19acc5b1efc6c8a099aed7f7facda7767be2938eb0809ee0ad#2",
                "dc8f71afa6df7a7366b8a328afee87c0df6b3b966c351cc348495334f613765b#2");
//        String txWithoutSign = utxoController.getTxWithoutSign(strings, receiverData);
//        System.out.println(txWithoutSign);
    }
}
