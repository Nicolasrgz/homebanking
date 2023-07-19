package com.mindhub.homebanking.pdfs;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mindhub.homebanking.models.Transaction;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class PdfGenerator {
    public static void generatePdfFromTransactions(List<Transaction> transactions, String filePath) throws FileNotFoundException, DocumentException {
        // Create a new FileOutputStream instance
        FileOutputStream outputStream = new FileOutputStream(filePath);

        // Create a new Document instance
        Document document = new Document();

        // Create a new PdfWriter instance
        PdfWriter.getInstance(document, outputStream);

        // Open the document
        document.open();

        // Add a title to the document
        document.add(new Paragraph("List of transactions"));

        // Create a table to display the transactions
        PdfPTable table = new PdfPTable(new float[]{1, 2, 2, 2});
        table.addCell("ID");
        table.addCell("DATE");
        table.addCell("DESCRIPTION");
        table.addCell("AMOUNT");

        // Add the transactions to the table
        for (Transaction transaction : transactions) {
            table.addCell(String.valueOf(transaction.getId()));
            table.addCell(transaction.getDate().toString());
            table.addCell(transaction.getDescription());
            table.addCell(String.valueOf(transaction.getAmount()));
        }

        // Add the table to the document
        document.add(table);

        // Close the document
        document.close();
    }
}
