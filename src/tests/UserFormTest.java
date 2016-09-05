package tests;

import model.UserForm;
import model.UserFormColumn;
import org.junit.Assert;
import org.junit.Test;

import static tests.TestFramework.VIEW_NAME;

public class UserFormTest {
    @Test
    public void test_addMenu_returns_itself(){
        UserForm form1 = new UserForm(VIEW_NAME);
        UserForm form2 = form1.addMenu("", "");

        Assert.assertEquals("Expected .addMenu() returns initial UserForm-object.", form1.hashCode(), form2.hashCode());
    }

    @Test
    public void test_addView_returns_itself(){
        UserForm form1 = new UserForm(VIEW_NAME);
        UserForm form2 = form1.addView();

        Assert.assertEquals("Expected .addMenu() returns initial UserForm-object.", form1.hashCode(), form2.hashCode());
    }

    @Test
    public void test_getColumns_count(){
        UserForm form = new UserForm(VIEW_NAME)
                .addMenu("parent_item", "current_item")
                .addView();

        int expected = 33;
        int actual = form.getColumns().size();
        Assert.assertEquals("Expected another value 'id'-field.", expected, actual);
    }

    @Test
    public void test_getColumns_value(){
        UserForm form = new UserForm(VIEW_NAME)
                .addMenu("parent_item", "current_item")
                .addView();

        String expected = "id";
        String actual = form.getColumn("id").get("key");
        Assert.assertEquals("Expected another value 'id'-field.", expected, actual);
    }

    @Test
    public void testGetColumn_add_attributes_in_one_column() throws Exception {
        UserForm form = new UserForm(VIEW_NAME)
                .addMenu("parent_item", "current_item")
                .addView();

        UserFormColumn column = form.getColumn("id");
        column.put("key2", "value2");
        column.put("key3", "value3");
        column.put("key4", "value4");

        Assert.assertEquals("Expected all attributes will be in one column.", "value2", column.get("key2"));
        Assert.assertEquals("Expected all attributes will be in one column.", "value3", column.get("key3"));
        Assert.assertEquals("Expected all attributes will be in one column.", "value4", column.get("key4"));
    }
}