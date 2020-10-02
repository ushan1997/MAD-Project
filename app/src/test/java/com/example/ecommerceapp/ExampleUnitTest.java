package com.example.ecommerceapp;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private ProductDetailsActivity productDetailsActivity;

    @Before
    public void setUp() {
        productDetailsActivity = new ProductDetailsActivity();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


}

