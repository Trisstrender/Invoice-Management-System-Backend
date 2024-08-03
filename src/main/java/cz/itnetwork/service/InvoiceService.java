package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;

import java.util.List;
import java.util.Map;

/**
 * Service interface for Invoice-related operations.
 * This interface defines the contract for invoice management operations.
 */
public interface InvoiceService {

    /**
     * Creates a new invoice.
     *
     * @param invoiceDTO The invoice data to create
     * @return The created invoice DTO
     */
    InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);

    /**
     * Retrieves an invoice by its ID.
     *
     * @param id The ID of the invoice to retrieve
     * @return The retrieved invoice DTO
     */
    InvoiceDTO getInvoiceById(long id);

    /**
     * Updates an existing invoice.
     *
     * @param id The ID of the invoice to update
     * @param invoiceDTO The updated invoice data
     * @return The updated invoice DTO
     */
    InvoiceDTO updateInvoice(long id, InvoiceDTO invoiceDTO);

    /**
     * Deletes an invoice by its ID.
     *
     * @param id The ID of the invoice to delete
     */
    void deleteInvoice(long id);

    /**
     * Retrieves a list of invoices based on given parameters.
     *
     * @param params Map of parameters for filtering and pagination
     * @return List of invoice DTOs matching the criteria
     */
    List<InvoiceDTO> getInvoices(Map<String, String> params);

    /**
     * Retrieves invoice statistics.
     *
     * @return Map of invoice statistics
     */
    Map<String, Object> getInvoiceStatistics();

    /**
     * Retrieves sales invoices for a specific person.
     *
     * @param identificationNumber The identification number of the person
     * @return List of invoice DTOs representing the person's sales
     */
    List<InvoiceDTO> getPersonSales(String identificationNumber);

    /**
     * Retrieves purchase invoices for a specific person.
     *
     * @param identificationNumber The identification number of the person
     * @return List of invoice DTOs representing the person's purchases
     */
    List<InvoiceDTO> getPersonPurchases(String identificationNumber);
}