package org.scalablecapital.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleSearchHelper {

    private static Pattern patternForDomainName;
    private static final String DOMAIN_NAME_PATTERN = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";

    static {
        patternForDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
    }

    private GoogleSearchHelper(){

    }

    private static String getDomainName(String url){

        String domainName = "";
        Matcher matcher = patternForDomainName.matcher(url);
        if (matcher.find()) {
            domainName = matcher.group(0).toLowerCase().trim();
            if(url.startsWith("/url?q=https://"))
                domainName = "https://"+domainName;
            else if(url.startsWith("/url?q=http://"))
                domainName = "http://"+domainName;
        }
        return domainName;

    }

    public static CompletableFuture<Set<String>> searchGoogle(String query, int num, final int timeout) {

        final String request = "https://www.google.com/search?q=" + query + "&num="+num;
        System.out.println(request);
        CompletableFuture<Set<String>> future = CompletableFuture.supplyAsync(new Supplier<Set<String>>() {
            public Set<String> get() {
                Set<String> result = new HashSet<String>();
                try {
                    Document doc = Jsoup
                            .connect(request)
                            .userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
                            .timeout(timeout).get();
                    System.out.println(doc.title());
                    Elements links = doc.select("a[href]");
                    for (Element link : links) {

                        String temp = link.attr("href");
                        if(temp.startsWith("/url?q=")){
                            result.add(getDomainName(temp));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
        });
        return future;
    }

}
