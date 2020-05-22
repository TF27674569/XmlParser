package org.xml.parser.tools;

import android.text.TextUtils;
import org.xml.parser.annotation.Label;
import org.xml.parser.base.AbsParser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class XmlParserImpl<T> extends AbsParser {

    private T mCurrent;
    private ReflexField mReflex;
    private String mCurrentLabel;
    private List<T> xmlData;


    public static <T> XmlParserImpl<T> generateXmlParser(Class<T> clazz) {
        return new XmlParserImpl<>(clazz);
    }


    private XmlParserImpl(Class<T> clazz) {
        mReflex = new ReflexField(clazz);
        xmlData = new ArrayList<>();
    }


    @Override
    protected void onParserDocument() {

    }


    @Override
    protected void onParserLabel(String label) {
        // 根节点 创建 mCurrent
        if (label.equals(mReflex.root())) {
            if (mCurrent == null) {
                mCurrent = mReflex.newInstance();
            }
        }
        mCurrentLabel = label;
    }


    @Override
    protected void onParserAttribute(String attributeKey, String attributeValue) {
        Field attrField = mReflex.getAttrField(attributeKey);
        if (attrField != null) {
            attrField.setAccessible(true);
            try {
                attrField.set(mCurrent, attributeValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onParserText(String text) {
        Field labelField = mReflex.getLabelField(mCurrentLabel);
        if (labelField != null) {
            labelField.setAccessible(true);
            Label aLabel = labelField.getAnnotation(Label.class);
            if (aLabel.only()) {
                contains(text, labelField,aLabel.label());
            }

            try {
                labelField.set(mCurrent, text);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onEndLabel(String label) {
        if (TextUtils.equals(label, mReflex.root())) {
            xmlData.add(mCurrent);
            mCurrent = null;
        }
    }


    @Override
    protected void onEndDocument() {

    }

    private void contains(String text, Field field,String label) {
        for (T xml : xmlData) {
            try {
                String data = (String) field.get(xml);
                if (TextUtils.equals(data, text)) {
                    throw new IllegalStateException(label+" \"" + text + " \" is exists!");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public List<T> getXmlData() {
        return xmlData;
    }
}
