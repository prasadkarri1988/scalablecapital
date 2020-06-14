package org.scalablecapital.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PageInspector {


    private static final List<String> jsLibNames = Arrays.asList("jquery", "bootstrap", "angular", "vue", "modernizr", "backbone");

    private PageInspector(){

    }
    private static String getJavascriptLibraryName(String url) {
        for (String jsLibName : jsLibNames) {
            if (url.toLowerCase().contains(jsLibName))
                return jsLibName;
        }
        return "others";
    }


    public static Set<String> getJavascripts(String request, int timeout) {

        Set<String> result = new HashSet<String>();
        try {
            Document doc = Jsoup
                    .connect(request)
                    .userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
                    .timeout(timeout).get();
            Elements scripts = doc.getElementsByTag("script");
            for (Element script : scripts) {
                if (script.hasAttr("src")) {
                    result.add(getJavascriptLibraryName(script.attr("src")));
                }
            }
        } catch (IOException e) {
            System.err.println("problem accessing page at:" + request + " reason:" + e.getMessage());
        }
        return result;
    }

}