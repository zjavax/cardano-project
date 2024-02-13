package com.zjavax.cardanoproject;

import com.bloxbean.cardano.client.api.exception.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CardanoProjectApplication.class)
public class UtxoControllerTest {

    @Autowired
    public UtxoController utxoController;

    String address = "addr1qxu7nks0vt5te3dx2wmwq5ytz7td8hsvytl2zwkrjvwm0vmy3va7sk0l7yrpe9m3s3230ynqef8p0997ddhvkpkrkuysdhwdrg";


    @Test
    public void test() throws ApiException {
        utxoController.getUtxoByAddress(address);
    }
}
