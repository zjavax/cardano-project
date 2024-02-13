package com.zjavax.cardanoproject;

import com.bloxbean.cardano.client.api.exception.ApiException;
import com.bloxbean.cardano.client.api.model.Result;
import com.bloxbean.cardano.client.api.model.Utxo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/utxo")
public class UtxoController extends QuickTxBaseIT {

    @GetMapping("/{addressOrStakeAddress}")
    public Result<List<Utxo>> getUtxoByAddress(@PathVariable String address) throws ApiException {
        Result<List<Utxo>> utxos = getBackendService().getUtxoService().getUtxos(address, 100, 1);
        return utxos;
    }

}
