package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.model.dtos.BaseResponse;
import com.microservice.inventoryservice.model.dtos.OrderItemRequest;
import com.microservice.inventoryservice.model.entities.Inventory;
import com.microservice.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    /* O método isInStock(String sku) verifica se um determinado SKU está em estoque. Ele chama o método findBySku(sku) do inventoryRepository para
    obter o inventário correspondente ao SKU fornecido. Em seguida, ele verifica se a quantidade desse item no inventário é maior que zero e retorna
    um booleano com base nessa verificação.*/
    public boolean isInStock(String sku){
        var inventory = inventoryRepository.findBySku(sku);
        return inventory.filter(value -> value.getQuantity() > 0).isPresent();
    }

    /* O método areInStock(List<OrderItemRequest> orderItems) verifica se uma lista de itens de pedido está em estoque. Ele itera sobre os itens de
    pedido fornecidos, obtém a lista de inventário correspondente aos SKUs desses itens usando o método findBySkuIn(skus) do inventoryRepository, e
    então verifica se cada item está disponível em estoque e em quantidade suficiente.
    Durante a iteração, ele constrói uma lista de erros caso um item não exista no inventário ou tenha quantidade insuficiente. No final, ele retorna
    um objeto BaseResponse contendo a lista de erros, se houver algum, ou nulo se não houver erros.*/
    public BaseResponse areInStock(List<OrderItemRequest> orderItems){

        var errorList = new ArrayList<String>();

        List<String> skus = orderItems.stream().map(OrderItemRequest::getSku).toList();

        List<Inventory> inventoryList = inventoryRepository.findBySkuIn(skus);

        orderItems.forEach(orderItem -> {
            var inventory = inventoryList.stream().filter(value -> value.getSku().equals(orderItem.getSku())).findFirst();
            if(inventory.isEmpty()){
                errorList.add("Produto com sku " + orderItem.getSku() + " não existe");
            }else if(inventory.get().getQuantity() < orderItem.getQuantity()){
                errorList.add("Produto com sku " + orderItem.getSku() + " tem quantidade insuficiente");
            }
        });

        return errorList.size() > 0 ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }
}
