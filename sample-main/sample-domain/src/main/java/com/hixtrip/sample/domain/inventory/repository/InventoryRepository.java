package com.hixtrip.sample.domain.inventory.repository;

public interface InventoryRepository {

    /**
     * 获取sku当前库存
     *
     * @param skuId skuId
     * @return 当前库存数量
     */
    Integer getInventory(String skuId);

    /**
     * 修改库存
     *
     * @param skuId               商品SKU ID
     * @param sellableQuantity    可售库存变更量
     * @param withholdingQuantity 预占库存变更量
     * @param occupiedQuantity    占用库存变更量
     * @return 是否修改成功
     */
    Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity);

}
