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
import com.bloxbean.cardano.client.exception.CborSerializationException;
import com.bloxbean.cardano.client.quicktx.QuickTxBuilder;
import com.bloxbean.cardano.client.quicktx.Tx;
import com.bloxbean.cardano.client.transaction.spec.Transaction;
import com.bloxbean.cardano.client.util.JsonUtil;
import com.zjavax.cardanoproject.entity.ReceiverData;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/baseSetting")
public class BaseSettingController extends QuickTxBaseIT {

//    @GetMapping("/networks")
//    public Result<List<Utxo>> getUtxoByAddress(@RequestParam String network) throws ApiException {
//
//
//
////        return getBackendService().getUtxoService().getUtxos(address, count, page);
//    }





}
