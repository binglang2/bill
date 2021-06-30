package com.banma.bill;

import com.banma.bill.mp.MpConfig;
import com.banma.bill.mp.MpGenerator;

/**
 * @author binglang
 * @since 2019/6/26
 */
public class EntityGenerator {

    public static void main(String[] args) {
        MpConfig mpConfig = new MpConfig()
            .setPackageName("com.banma.bill")
            .setDbName("bill")
            .setTables("transaction_book", "transaction_category", "transaction_fixed", "transaction_flow", "user_wx", "user_feedback");
        new MpGenerator(mpConfig).execute();
    }

}
