package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-03-21.
 */
public class Hand {

    private List<String> content = new ArrayList<>();

    public Hand() {
    }

    public void addContent(String contentStr) {
        content.add(contentStr);
    }

    public List<String> getContent() {
        return content;
    }

}