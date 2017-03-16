package com.example.ecnill.postviewer;

import com.example.ecnill.postviewer.Entities.Owner;
import com.example.ecnill.postviewer.Utils.Utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsUnitTest {

    @Test
    public void replaceLastCharIsCorrect() throws Exception {
        String url = "www.example.com/1";
        assertEquals(Utils.replaceLastChar(url, 20), "www.example.com/20");
    }

    @Test
    public void compareOwnerEntityIsCorrect() throws Exception {
        Owner o1 = new Owner();
        o1.setId(1);
        o1.setProfileImageUrl("url.com/1.jpg");
        o1.setName("username");
        o1.setReputation(5);
        Owner o2 = new Owner();
        o2.setId(1);
        o2.setProfileImageUrl("url.com/1.jpg");
        o2.setName("username");
        o2.setReputation(5);
        assertTrue(o1 != o2);
    }

}