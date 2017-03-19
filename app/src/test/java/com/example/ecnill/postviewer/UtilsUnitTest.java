package com.example.ecnill.postviewer;

import com.example.ecnill.postviewer.Utils.Utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsUnitTest {

    @Test
    public void replaceLastCharIsCorrect() throws Exception {
        String url = "www.example.com/1";
        assertEquals(Utils.replaceLastChar(url, 20), "www.example.com/20");
    }

}