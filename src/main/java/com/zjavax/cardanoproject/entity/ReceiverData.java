package com.zjavax.cardanoproject.entity;

import com.bloxbean.cardano.client.backend.model.Asset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ReceiverData {

    private String receiverAddress;
    private String feePayerAddress;
    private int signersCount;
    private List<Asset> assetList;
}
