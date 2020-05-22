package org.xml.parser.base;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public interface IParser {

    void parser(XmlResourceParser parser) throws XmlPullParserException, IOException;

}
