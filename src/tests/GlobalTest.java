package tests;

import controller.Global;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static tests.TestFramework.VIEW_NAME;

public class GlobalTest {
    private final static String TEST_PATH = "test";
    private final static String WRONG_PATH = "test1";

    @Before
    public void setUp() throws Exception {
        Global.delete(TEST_PATH);
    }

    @Test
    public void checkExists() {
        Global.createFolder(TEST_PATH);

        Assert.assertTrue("Expected creation test folder", Global.checkExists(TEST_PATH));
        Assert.assertFalse("Expected test folder will be only onr", Global.checkExists(WRONG_PATH));
    }

    @Test
    public void getFile_ignore_case(){
        String fileName = VIEW_NAME;

        File file1 = Global.getFile(fileName);
        File file2 = Global.getFile(fileName.toLowerCase());

        Assert.assertEquals("Expected file will be found with ignore case.", file1.hashCode(), file2.hashCode());
    }

    @After
    public void tearDown() throws Exception {
        Global.delete(TEST_PATH);
    }
}