package com.github.sewerina.reek;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.github.sewerina.reek.ui.MainViewModel;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {
    @Rule
    public InstantTaskExecutorRule mTestRule = new InstantTaskExecutorRule();

    @Test
    public void testMapScreenPath() {
        MainViewModel viewModel = new MainViewModel();
        viewModel.setMapScreenPath("abcPath");
        assertEquals("abcPath", viewModel.mapScreenPath().getValue());
    }

    @Test
    public void testHasMapScreen() {
        MainViewModel viewModel = new MainViewModel();
        viewModel.setMapScreenPath("abcPath");
        assertTrue(viewModel.hasMapScreen());
    }

    @Test
    public void testCheckRecipient() {
        MainViewModel viewModel = new MainViewModel();
        viewModel.checkRecipient(2, true);
        assertTrue(viewModel.mRecipientList.get(2).mIsSelected);
    }

    @Test
    public void testBody() {
        MainViewModel viewModel = new MainViewModel();
        assertNotNull(viewModel.body());
        assertTrue(!viewModel.body().isEmpty());
    }

}