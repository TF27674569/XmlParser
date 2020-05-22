package org.xml.parser.tools;

import android.content.res.XmlResourceParser;

import java.util.List;

public class XmlResolver {

    public static  <T> List<T> toBean(Class<T> clazz, XmlResourceParser parser) {
        XmlParserImpl<T> xmlParser = XmlParserImpl.generateXmlParser(clazz);
        try {
            xmlParser.parser(parser);
            return xmlParser.getXmlData();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
