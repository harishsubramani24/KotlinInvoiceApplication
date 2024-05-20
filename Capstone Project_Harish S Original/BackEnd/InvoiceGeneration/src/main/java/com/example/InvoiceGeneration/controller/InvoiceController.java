package com.example.InvoiceGeneration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.InvoiceGeneration.entity.Invoice;
import com.example.InvoiceGeneration.entity.StringResponse;
import com.example.InvoiceGeneration.service.InvoiceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/createInvoice")
    public ResponseEntity<StringResponse> createInvoice(@RequestBody Invoice invoice, @RequestParam Long userId) {
        logger.info("Creating new invoice for userId {}", userId);
        try {
            Invoice createdInvoice = invoiceService.createInvoice(invoice, userId);
            StringResponse response = new StringResponse();response.setResponse("Invoice created successfully with ID " +  createdInvoice.getId());
            logger.info("Created invoice with ID {}", createdInvoice.getId());
            return new ResponseEntity<> (response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error creating invoice: {}", e.getMessage());
            StringResponse response = new StringResponse();response.setResponse("Failed To Create Invoice ");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateInvoice")
    public ResponseEntity<StringResponse> updateInvoice(@RequestBody Invoice invoice, @RequestParam Long userId) {
        logger.info("Updating invoice with ID {} for user ID {}", invoice.getId(), userId);
        try {
         
        	
            Invoice updatedInvoice = invoiceService.updateInvoice(invoice,userId);
            logger.info("Updated invoice with ID {}", updatedInvoice.getId());
            StringResponse response = new StringResponse();
            response.setResponse("Invoice updated successfully with ID "+updatedInvoice.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating invoice: {}", e.getMessage());
            StringResponse response = new StringResponse();
            response.setResponse("Failed to update invoice");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getInvoices")
    public ResponseEntity<?> getInvoices(@RequestParam Long userId) {
        logger.info("Fetching invoices for userId {}", userId);
        try {
            List<Invoice> invoices = invoiceService.getInvoicesByUserId(userId);
            logger.info("Fetched {} invoices for userId {}", invoices.size(), userId);
            if (invoices.isEmpty()) {
                return new ResponseEntity<>("No invoices found for userId " + userId, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(invoices, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching invoices for userId {}: {}", userId, e.getMessage());
            return new ResponseEntity<>("Failed to fetch invoices", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getInvoicesByUserId")
    public ResponseEntity<?> getInvoicesByUserId(@RequestParam Long userId) {
        logger.info("Fetching invoices for userId {}", userId);
        try {
            List<Invoice> invoices = invoiceService.getInvoicesByUserId(userId);
            logger.info("Fetched {} invoices for userId {}", invoices.size(), userId);
            if (invoices.isEmpty()) {
                return new ResponseEntity<>("No invoices found for userId " + userId, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(invoices, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching invoices for userId {}: {}", userId, e.getMessage());
            return new ResponseEntity<>("Failed to fetch invoices", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getInvoiceById")
    public ResponseEntity<?> getInvoiceById(@RequestParam Long id) {
        logger.info("Fetching invoice with ID {}", id);
        try {
            Invoice invoice = invoiceService.getInvoiceById(id);
            if (invoice == null) {
                logger.warn("Invoice with ID {} not found", id);
                return new ResponseEntity<>("Invoice with ID " + id + " not found", HttpStatus.NOT_FOUND);
            }
            logger.info("Fetched invoice with ID {}", invoice.getId());
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching invoice with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>("Failed to fetch invoice", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/invoiceDelete")
    public ResponseEntity<String> invoiceDelete(@RequestParam Long id) {
        logger.info("Deleting invoice with ID {}", id);
        try {
            String result = invoiceService.invoiceDelete(id);
            if (result.equals("Delete Successfully")) {
                logger.info("Deleted invoice with ID {}", id);
                return new ResponseEntity<>("Invoice deleted successfully with ID " + id, HttpStatus.OK);
            } else {
                logger.warn("Failed to delete invoice with ID {}", id);
                return new ResponseEntity<>("Failed to delete invoice with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error deleting invoice with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>("Failed to delete invoice", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}