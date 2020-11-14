package com.bancomext.fiducia.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.SoundPool;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.tools.Constants;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.link.DefaultLinkHandler;
import com.github.barteksc.pdfviewer.link.LinkHandler;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.model.LinkTapEvent;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

public class AvisosPrivacidadActivity extends AppCompatActivity implements LinkHandler,View.OnClickListener, OnPageErrorListener, OnPageChangeListener, OnLoadCompleteListener {

    PDFView aviso;
    ImageButton regresar;
    private FirebaseAnalytics mFirebaseAnalytics;
    public static final String SAMPLE_FILE = "aviso.pdf";
    String pdfFileName;
    Integer pageNumber;
    private static final String TAG = AvisosPrivacidadActivity.class.getSimpleName();
    private Object LinkHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos_privacidad);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(Constants.aviso_privacidad, null);
        setView();
    }

    private void setView() {

        regresar = findViewById(R.id.ibRegresar);
        regresar.setOnClickListener(this);

        aviso = findViewById(R.id.pdfView);
        aviso.setVerticalScrollBarEnabled(true);
        pdfFileName = SAMPLE_FILE;
        aviso.fromAsset(SAMPLE_FILE)
                .defaultPage(0)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == regresar.getId()){
            Intent intent = new Intent(AvisosPrivacidadActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = aviso.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());

        printBookmarksTree(aviso.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public void onPageError(int page, Throwable t) {
        Log.e(TAG, "Cannot load page " + page);
    }

    @Override
    public void handleLinkEvent(LinkTapEvent event) {
        String uri = event.getLink().getUri();
        Integer page = event.getLink().getDestPageIdx();
        if (uri != null && !uri.isEmpty()) {
            handleUri(uri);
        } else if (page != null) {
            handlePage(page);
        }
    }

    private void handleUri(String uri) {
        Uri parsedUri = Uri.parse(uri);
        Intent intent = new Intent(Intent.ACTION_VIEW, parsedUri);
        Context context = aviso.getContext();
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Log.w(TAG, "No activity found for URI: " + uri);
        }
    }

    private void handlePage(int page) {
        aviso.jumpTo(page);
    }
}
