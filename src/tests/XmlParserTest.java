package tests;

import controller.XmlParser;
import org.junit.Assert;
import org.junit.Test;

public class XmlParserTest {
    final String FILE_NAME = "src\\tests\\Requests.XML";
    final String TAG_WINDOW_CAPTION = "DataFormProperties.WindowCaption";
    final String TAG_GRID_LAYOUT_XML = "DataFormProperties.GridLayoutXml";
    final String TAG_ROW_COLOR_COLUMN_NAME = "Grid.GridProperties.RowColorColumnName";

    @Test
    public void test_get_property(){
        XmlParser parser = new XmlParser();
        parser.parse(FILE_NAME);

        String expected = "Запросы";
        String actual = parser.get(TAG_WINDOW_CAPTION);
        Assert.assertEquals("Expected another tag '" + TAG_WINDOW_CAPTION + "' value.", expected, actual);
    }

    @Test
    public void test_parse_text(){
        XmlParser parser = new XmlParser();
        parser.parse(FILE_NAME);

        String xml = parser.get(TAG_GRID_LAYOUT_XML);
        parser.parse(xml);

        String expected = "Color";
        String actual = parser.get(TAG_ROW_COLOR_COLUMN_NAME);
        Assert.assertEquals("Expected second-level tag value.", expected, actual);
    }
}