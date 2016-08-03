package controller;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class XmlParser {
    private final static String DTD = "<?xml version='1.0' encoding='windows-1251' ?>";
    private HashMap<String, String> _file;

    public XmlParser() {
        _file = new HashMap<>();
    }

    public void parse(final String value) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;

            if(isFilePath(value)){
                doc = builder.parse(value);
            } else {
                String fileText = value.contains(DTD)? value: DTD + "\n" + value;
                doc = builder.parse(new InputSource(new ByteArrayInputStream(fileText.getBytes("windows-1251"))));
            }

            _file.putAll(getNodes(doc, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String paramName) {
        return _file.get(paramName);
    }

    private boolean isFilePath(String param) {
        try {
            File file = new File(param);
            file.getCanonicalPath();
            return true;
        } catch (IOException e) {}

        return false;
    }

    private HashMap<String, String> getNodes(Node node, String parentPrefix){
        HashMap<String, String> result = new HashMap<>();

        result.putAll(addCurrentNode(node, parentPrefix));
        //result.putAll(addChildNodes(node));

        return result;
    }

    private HashMap<String, String> addCurrentNode(final Node node, final String parentPrefix) {
        HashMap<String, String> result = new HashMap<>();

        String name = "";
        String value = node.getTextContent();
        if(node.getNodeType() == Node.ELEMENT_NODE){
            name = getNodeNamePrefix(parentPrefix) + node.getNodeName() + getNodeNameSuffix(node);
            result.put(name, value);
        }

        result.putAll(addChildNodes(node, name));
        return result;
    }

    private HashMap<String, String> addChildNodes(final Node node, final String parentName) {
        HashMap<String, String> result = new HashMap<>();

        NodeList list = node.getChildNodes();
        for(int i=0; i<list.getLength(); i++){
            result.putAll(getNodes(list.item(i), parentName));
        }

        return result;
    }

    private String getNodeNamePrefix(final String parentPrefix){
        return parentPrefix.equals("") || parentPrefix.equals("#document") ? "": parentPrefix + ".";
    }

    private String getNodeNameSuffix(Node node) {
        Node parent = node.getParentNode();
        String name = node.getNodeName();

        int count = getSimilarNodeCount(parent, name);
        if(count > 1){
            return "(" + getNodeKey(node) + ")";
        }

        return "";
    }

    private String getNodeKey(Node node) {
        NodeList children = node.getChildNodes();

        for(int i=0; i<children.getLength(); i++){
            if(children.item(i).getNodeName().equals("Key")){
                return children.item(i).getTextContent();
            }
        }

        return "";
    }

    private int getSimilarNodeCount(Node parent, String name) {
        int count = 0;
        for(int i=0; i<parent.getChildNodes().getLength(); i++){
            if(parent.getChildNodes().item(i).getNodeName().equals(name)){
                count++;
            }
        }

        return count;
    }
}
