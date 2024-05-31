package com.project.project.service;

import com.project.project.responHandler.CustomResponse;
import com.project.project.dto.TransactionDetailReq;
import com.project.project.dto.TransactionsReq;
import com.project.project.entity.Product;
import com.project.project.entity.TransactionDetail;
import com.project.project.entity.Transactions;
import com.project.project.repository.ProductRepo;
import com.project.project.repository.TransactionDetailRepo;
import com.project.project.repository.TransactionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransactionsService {
    @Autowired
    private TransactionsRepo transactionsRepo;

    @Autowired
    private TransactionDetailRepo transactionDetailRepo;

    @Autowired
    private ProductRepo productRepo;

    public CustomResponse createTransactions(TransactionsReq transactionsReq){
        try{
            //Long charge = transactionsReq.getTotalPay() - transactionsReq.getTotalAmount();
            Transactions transactions = convertToTransactionEntity(transactionsReq);
            transactions.setTransactionDate(LocalDateTime.now());
            transactions = transactionsRepo.save(transactions);

            CustomResponse response = new CustomResponse();
            response.setStatus("ok");
            response.setMessage("success" ); //"\nYour charge: " + charge);
            return response;
        } catch (Exception e){
            CustomResponse response = new CustomResponse();
            response.setStatus("failed");
            response.setMessage("data was not added successfully: "+ e.getMessage());
            return response;
        }
    }

    public List<Map<String, Object>> getAllTransactions(){
        try {
            List<Transactions> transactions = transactionsRepo.findAll();
            List<Map<String, Object>> simpleTransactions = new ArrayList<>();

            for (Transactions transaction : transactions) {
                Map<String, Object> simpleTransaction = new HashMap<>();
                simpleTransaction.put("transaction_date", transaction.getTransactionDate());
                simpleTransaction.put("total_pay", transaction.getTotalPay());
                simpleTransaction.put("total_amount", transaction.getTotalAmount());
                simpleTransactions.add(simpleTransaction);
            }
            return simpleTransactions;
        }catch (Exception e){
            return Collections.emptyList();
        }

    }


    private Transactions convertToTransactionEntity (TransactionsReq transactionsReq){
        Transactions transactions = new Transactions();
        if (transactionsReq.getTotalAmount() == null || transactionsReq.getTotalAmount() <= 0) {
            throw new IllegalArgumentException("Total Amount be specified and greater than zero");
        }
        transactions.setTotalAmount(Math.toIntExact(transactionsReq.getTotalAmount()));
        if (transactionsReq.getTotalPay() == null || transactionsReq.getTotalPay() <= 0){
            throw new IllegalArgumentException("Total Pay must be specified and greater than zero");
        }
        transactions.setTotalPay(Math.toIntExact(transactionsReq.getTotalPay()));

        if (transactionsReq.getTotalPay() < transactionsReq.getTotalAmount()){
            throw new IllegalArgumentException("Total Pay can't less than Total Amount");
        }

        transactions.setTransactionDate(transactionsReq.getTransactionDate());

        List<TransactionDetail> transactionDetails = new ArrayList<>();
        int totalAmount = 0;

        for (TransactionDetailReq detailReq : transactionsReq.getTransactionDetailReqList()){
            TransactionDetail detail = new TransactionDetail();

            Product product = getProductId(detailReq.getProductId());

            detail.setProduct(product);

            // Validasi untuk quantity
            if (detailReq.getQuantity() == null || detailReq.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be specified and greater than zero");
            }
            int quantity = Math.toIntExact(detailReq.getQuantity());
            detail.setQuantity(quantity);

            // Validasi untuk subtotal
            int subtotal = Math.toIntExact(detailReq.getQuantity() * product.getPrice());
            if (detailReq.getSubtotal() == null || detailReq.getSubtotal() <= 0) {
                throw new IllegalArgumentException("Subtotal must be specified and greater than zero");
            }

            //Cek subtotal = quantity*product price
            if (subtotal != quantity* product.getPrice()){
                throw new IllegalArgumentException("Subtotal does not match quantity * product price");
            }

            detail.setSubtotal(Math.toIntExact(detailReq.getSubtotal()));

            totalAmount += subtotal;
            detail.setTransaction(transactions);
            transactionDetails.add(detail);
        }

        if (totalAmount != transactions.getTotalAmount()) {
            throw new IllegalArgumentException("Please Check Total Amount or Subtotal (productId price * quantity)");
        }

        transactions.setTransactionDetails(transactionDetails);

        transactions = transactionsRepo.save(transactions);

        for(TransactionDetail detail : transactionDetails){
            detail.setTransaction(transactions);
        }

        transactionDetailRepo.saveAll(transactionDetails);

        return transactions;
    }

    private Product getProductId(int productId){
        return productRepo.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
    }

}
