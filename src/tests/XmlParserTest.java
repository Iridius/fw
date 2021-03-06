package tests;

import controller.XmlParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static controller.Global.*;
import static tests.TestFramework.FILE;

public class XmlParserTest {


    @Test
    public void test_get_property(){
        String expected = "Запросы";
        String actual = XmlParser.parse(FILE).get(TAG_WINDOW_CAPTION);

        Assert.assertEquals("Expected another tag '" + TAG_WINDOW_CAPTION + "' value.", expected, actual);
    }

    @Test
    public void test_parse_text(){
        String xml = XmlParser.parse(FILE).get(TAG_GRID_LAYOUT_XML);

        String expected = "Color";
        String actual = XmlParser.parse(xml).get(TAG_ROW_COLOR_COLUMN_NAME);

        Assert.assertEquals("Expected second-level tag value.", expected, actual);
    }
}