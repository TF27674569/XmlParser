package org.xml.parser.tools;

import android.text.TextUtils;

import org.xml.parser.annotation.Attr;
import org.xml.parser.annotation.Label;
import org.xml.parser.annotation.Root;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class ReflexField {

    private Map<String, Field> mLabels;
    private Map<String, Field> mAttrs;
    private String mRoot;
    private Class<?> mClazz;

    public ReflexField(Class<?> clazz) {
        mLabels = new HashMap<>();
        mAttrs = new HashMap<>();
        mClazz = clazz;
        init();
    }

    private void init() {
        // 解析 root标签
        Root root = mClazz.getAnnotation(Root.class);
        if (root == null || TextUtils.isEmpty(root.value())) {
            throw new NullPointerException("Root label is null.");
        }
        mRoot = root.value();

        // 解析标签上的 attribute 和 label
        Field[] declaredFields = mClazz.getDeclaredFields();
        for (Field field : declaredFields) {
            // 解析 attr
            Attr attr = field.getAnnotation(Attr.class);
            if (attr != null) {
                mAttrs.put(attr.value(), field);
            }

            // 解析 label
            Label label = field.getAnnotation(Label.class);
            if (label != null) {
                mLabels.put(label.label(), field);
            }
        }
    }

    /**
     * root 字段
     */
    public String root() {
        return mRoot;
    }

    /**
     * 创建一个对象
     */
    public <T> T newInstance() {
        T o = null;
        try {
            o = (T) mClazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return o;
    }

    /**
     * 获取属性值
     */
    public Field getAttrField(String key) {
        return mAttrs.get(key);
    }

    /**
     * 获取属性值
     */
    public Field getLabelField(String key) {
        return mLabels.get(key);
    }
}
