package com.example.ecnill.postviewer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.spy;

/**
 * Created by ecnill on 15.3.17.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainActivityUnitTest {

    @Mock
    private MainActivity activity;

    @Before
    public void setUp() {
        this.activity = spy(new MainActivity());
    }

    @Test(expected = Exception.class)
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

}
