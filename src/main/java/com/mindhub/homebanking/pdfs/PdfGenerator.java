package com.mindhub.homebanking.pdfs;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PdfGenerator {
    public static void generatePdfFromTransactions(List<Transaction> transactions,Account accounts, String filePath) throws IOException, DocumentException {
        // Create a new FileOutputStream instance
        FileOutputStream outputStream = new FileOutputStream(filePath);

        // Create a new Document instance
        Document document = new Document();

        // Create a new PdfWriter instance
        PdfWriter.getInstance(document, outputStream);

        // Open the document
        document.open();
        Image image = Image.getInstance(new URL("http://localhost:8080/web/img/logo.jpeg"));
        image.scaleAbsolute(100, 100);
        image.setAlignment(Element.ALIGN_CENTER);


        // Add the image to the document
        document.add(image);
        // Create a new font with the desired color
        Font font = new Font();
        font.setSize(15);
        font.setColor(BaseColor.BLUE);

        Font fontSize = new Font();
        fontSize.setSize(20);

        Paragraph paragraphH1 = new Paragraph("CLOVER BANK", fontSize);
        paragraphH1.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraphH1);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        // Add a paragraph with the colored font to the document
        Paragraph paragraph = new Paragraph("Account info", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        // Add a title to the document

        // Create a table to display the transactions
        PdfPTable table1 = new PdfPTable(new float[]{ 2, 2, 2});
        table1.addCell("NUMBER");
        table1.addCell("CREATION DATE");
        table1.addCell("BALANCE");

        Paragraph paragraph1 = new Paragraph("List of transactions", font);
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        // Add the transactions to the table

        table1.addCell(accounts.getNumber());
        table1.addCell(accounts.getCreationDate().toString());
        table1.addCell(String.valueOf(accounts.getBalance()));


        // Create a table to display the transactions
        PdfPTable table = new PdfPTable(new float[]{ 2, 2, 2, 2});
        table.addCell("DATE");
        table.addCell("DESCRIPTION");
        table.addCell("AMOUNT");
        table.addCell("TYPE");




        // Add the transactions to the table
        for (Transaction transaction : transactions) {
            table.addCell(transaction.getDate().toString());
            table.addCell(transaction.getDescription());
            table.addCell(String.valueOf(transaction.getAmount()));
            table.addCell(transaction.getType().name());
        }

        // Add the table to the document
        document.add(paragraph);
        document.add(new Paragraph(" "));
        document.add(table1);
        document.add(new Paragraph(" "));
        document.add(paragraph1);
        document.add(new Paragraph(" "));
        document.add(table);
        // Close the document
        document.close();
    }


}
