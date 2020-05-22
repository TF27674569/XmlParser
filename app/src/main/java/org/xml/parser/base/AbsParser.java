package org.xml.parser.base;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public abstract class AbsParser implements IParser {


    @Override
    public void parser(XmlResourceParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        label:
        while (true) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    onParserDocument();
                    break;
                case XmlPullParser.START_TAG:
                    onParserLabel(parser.getName());
                    int attributeCount = parser.getAttributeCount();
                    for (int i = 0; i < attributeCount; i++) {
                        onParserAttribute(parser.getAttributeName(i),parser.getAttributeValue(i));
                    }
                    break;
                case XmlPullParser.TEXT:
                    onParserText(parser.getText());
                    break;
                case XmlPullParser.END_TAG:
                    onEndLabel(parser.getName());
                    break;
                case XmlPullParser.END_DOCUMENT:
                    onEndDocument();
                    break label;
                default:
                    break;
            }
            eventType = parser.next();   //将当前解析器光标往下一步移
        }
    }

    /**
     * 开始解析
     */
    protected abstract void onParserDocument();

    /**
     * 开始解析标签
     */
    protected abstract void onParserLabel(String label);

    /**
     * 解析tag上的 attribute
     *
     * @param attributeKey key
     * @param attributeValue value
     */
    protected abstract void onParserAttribute(String attributeKey, String attributeValue);

    /**
     * 解析tag包裹的文本
     */
    protected abstract void onParserText(String text);

    /**
     * 解析某个标签结束
     */
    protected abstract void onEndLabel(String label);

    /**
     * 文档解析结束
     */
    protected abstract void onEndDocument();


}
