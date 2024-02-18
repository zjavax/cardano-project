package com.zjavax.cardanoproject.entity;

import com.bloxbean.cardano.client.backend.model.Asset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ReceiverData {

    private String receiverAddress;
    private String feePayerAddress;
    private int signersCount;
    private List<Asset> assetList;
    List<String> stakeAddressList;

    public List<Asset> getAssetList() {
        if(assetList == null) {
            assetList = new ArrayList<>();
        }
        return assetList;
    }

    public void setAssetList(List<Asset> assetList) {
        if(assetList == null) {
            assetList = new ArrayList<>();
        }
        this.assetList = assetList;
    }
}
