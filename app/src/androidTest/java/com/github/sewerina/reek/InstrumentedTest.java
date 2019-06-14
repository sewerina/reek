package com.github.sewerina.reek;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.github.sewerina.reek", appContext.getPackageName());
    }

    @Test
    public void testEmail() {
        MainViewModel viewModel = new MainViewModel();

        assertNotNull(viewModel.email());
        assertTrue(viewModel.email().isEmpty());

        viewModel.checkRecipient(1, true);
        viewModel.checkRecipient(4, true);

        assertNotNull(viewModel.email());
        assertTrue(!viewModel.email().isEmpty());

        viewModel.checkRecipient(4, false);
        assertNotNull(viewModel.email());
        assertTrue(!viewModel.email().isEmpty());

        viewModel.checkRecipient(1, false);
        assertNotNull(viewModel.email());
        assertTrue(viewModel.email().isEmpty());
    }

}
