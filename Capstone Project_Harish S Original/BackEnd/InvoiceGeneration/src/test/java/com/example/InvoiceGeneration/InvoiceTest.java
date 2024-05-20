package com.example.InvoiceGeneration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.InvoiceGeneration.entity.Invoice;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvoiceTest {

    private Invoice invoice;

    @BeforeEach
    public void setUp() {
        invoice = new Invoice();
        invoice.setId(1L);
        invoice.setId(1L);
        invoice.setClientName("Test Client");
        invoice.setAmount(100.0);
        invoice.setDescription("Test Description");
    }

    @Test
    public void testGetId() {
        assertEquals(1L, invoice.getId());
    }

    @Test
    public void testGetUserId() {
        assertEquals(1L, invoice.getId());
    }

    @Test
    public void testGetClientName() {
        assertEquals("Test Client", invoice.getClientName());
    }

    @Test
    public void testGetAmount() {
        assertEquals(100.0, invoice.getAmount());
    }

    

    @Test
    public void testGetDescription() {
        assertEquals("Test Description", invoice.getDescription());
    }

    @Test
    public void testSetId() {
        invoice.setId(2L);
        assertEquals(2L, invoice.getId());
    }

    @Test
    public void testSetUserId() {
        invoice.setId(2L);
        assertEquals(2L, invoice.getId());
    }

    @Test
    public void testSetClientName() {
        invoice.setClientName("New Test Client");
        assertEquals("New Test Client", invoice.getClientName());
    }

    @Test
    public void testSetAmount() {
        invoice.setAmount(200.0);
        assertEquals(200.0, invoice.getAmount());
    }

    
    @Test
    public void testSetDescription() {
        invoice.setDescription("New Test Description");
        assertEquals("New Test Description", invoice.getDescription());
    }
}
