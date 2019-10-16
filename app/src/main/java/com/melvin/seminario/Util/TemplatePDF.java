package com.melvin.seminario.Util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TemplatePDF {

    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fuenteTitulo = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fuenteSubtitulo = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font fuenteTexto = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private Font fuenteHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.RED);

    public TemplatePDF(Context context) {
        this.context = context;
    }

    public void openDocument() throws Exception {
        createFile();
        document = new Document(PageSize.A4);
        pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        document.open();
    }

    public void createFile() {

        File folder = new File(Environment.getExternalStorageDirectory().toString(), "PDF");

        if (!folder.exists())
            folder.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.GERMANY).format(new Date());
        String pdfFileName = "PDF_" + timeStamp;

        pdfFile = new File(folder, pdfFileName + ".pdf");
    }

    public void closeDocument(){
        document.close();
    }

    public void addMetaData(String titulo, String tema, String autor){
        document.addTitle(titulo);
        document.addSubject(tema);
        document.addAuthor(autor);
    }

    public void addCampo(String titulo, String texto) throws Exception{
        paragraph= new Paragraph();
        addChildParagraph(new Paragraph(titulo+ ":", fuenteTitulo));
        document.add(paragraph);
        paragraph = new Paragraph(texto, fuenteTexto);
        paragraph.setSpacingAfter(5);
        paragraph.setSpacingBefore(5);
        document.add(paragraph);
    }

    private void addChildParagraph(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childParagraph);
    }

    public void viewPdf(Activity activity){
        if (pdfFile.exists()){
            Uri uri = FileProvider.getUriForFile(activity, "com.melvin.seminario", pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                activity.startActivity(intent);
            }catch (ActivityNotFoundException e){
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
                Toast.makeText(activity, "No posees una aplicacion para abrir el PDF", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "No se encuentra el archivp", Toast.LENGTH_SHORT).show();
        }
    }
}


