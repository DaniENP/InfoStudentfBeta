package com.example.infostudentfbeta.DigitalFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.infostudentfbeta.R;

public class Siafragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_sia,container,false);
        WebView webView = (WebView)v.findViewById(R.id.webview_sia);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://autenticasia.unal.edu.co/oam/server/obrareq.cgi?encquery%3DF%2BlIfaW3p87H0bfhx3GE7IaCQAJOdETMWU%2F9Jp9YNGzDaqMceV8GfC5QixwlFGdcgXL0Dy6w9Wlzg%2Fc8Wqh0dK3iizvGV3vScO2NJZJGL%2Bqb8E1Q5NtkhgMqIZqmnlbpJUGeD49VSs8j8AdghmJv9eFPXzglaO2KPkX50lhBsW4MKhsFGxuonh4qzcyj1BRZgSA6xKwUKCA%2BEf2vxhidDCQhboEE7Fi8SYUBu5X6R0OWil8quPeBdYkyqzKQjSGDVFFywl6AgLCjx%2B7MBOXzh8R9brKCeydiZexPfpxDYnM%3D%20agentid%3DWT_uncuxwebX%20ver%3D1%20crmethod%3D2&ECID-Context=1.005e6QUOERf6qIRMyYNa6G000h0M00CCvT%3BkXjE");
        return v;
    }
}
