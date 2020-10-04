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

    private ProductDetailsActivity productDetailsActivity;/**IT19753140/Gunaratne U.A/Metro Weekday*/

    private CartActivity cart;/**IT19753140/Gunaratne U.A/Metro Weekday*/



    @Before
    public void setUp() {

        productDetailsActivity = new ProductDetailsActivity();/**IT19753140/Gunaratne U.A/Metro Weekday*/
        cart =new CartActivity();/**IT19753140/Gunaratne U.A/Metro Weekday*/
    }

    @Test
    public void addition_isCorrect() {

        assertEquals(4, 2 + 2);/**IT19753140/Gunaratne U.A/Metro Weekday*/
    }

    @Test
    public void testGenerateId(){
        int test =productDetailsActivity.generateCartId();/**IT19753140/Gunaratne U.A/Metro Weekday*/

        assertEquals(0,test);/**IT19753140/Gunaratne U.A/Metro Weekday*/
    }

    @Test
    public void checkEligible(){

     int test = productDetailsActivity.showEligibleItemQuantity(11);/**IT19753140/Gunaratne U.A/Metro Weekday*/
             assertEquals( 1,test);/**IT19753140/Gunaratne U.A/Metro Weekday*/

    }
    @Test
    public void TestCalcAmount(){
        int test = cart.calcAmount(10000,34);/**IT19753140/Gunaratne U.A/Metro Weekday*/
        assertEquals(340000,test);/**IT19753140/Gunaratne U.A/Metro Weekday*/
    }


}

