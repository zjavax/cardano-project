package com.zjavax.cardanoproject.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CollectFromUtxo {
    List<String> utxoStrList;
    ReceiverData receiverData;
}
