package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;

/**
 * 支付策略接口
 *
 * @author linw
 * @desc 用于处理不同支付状态的逻辑
 * @create 2024/04/28
 **/
public interface PayStrategy {

    String handlePayResult(CommandPayDTO commandPayDTO);

}
