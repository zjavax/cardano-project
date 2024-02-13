package com.zjavax.cardanoproject;

import com.bloxbean.cardano.client.api.exception.ApiException;
import com.bloxbean.cardano.client.api.model.Result;
import com.bloxbean.cardano.client.api.model.Utxo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController()
@RequestMapping("/utxo")
public class UtxoController extends QuickTxBaseIT {

    @GetMapping("/getUtxoByAddress")
    public Result<List<Utxo>> getUtxoByAddress(@RequestParam String address, @RequestParam int count, @RequestParam int page) throws ApiException {
        return getBackendService().getUtxoService().getUtxos(address, count, page);
    }

}
