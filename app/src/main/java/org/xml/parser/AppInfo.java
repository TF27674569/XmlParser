package org.xml.parser;

import org.xml.parser.annotation.Attr;
import org.xml.parser.annotation.Label;
import org.xml.parser.annotation.Root;

@Root("app")
public class AppInfo {

    @Label(label = "package", only = true)
    public String packageName;

    @Label(label = "name")
    public String name;


    @Attr("test1")
    public String testAttr;


    @Attr("test2")
    public String testAttr1;

    @Override
    public String toString() {
        return "AppInfo{" +
                "packageName='" + packageName + '\'' +
                ", name='" + name + '\'' +
                ", testAttr='" + testAttr + '\'' +
                ", testAttr1='" + testAttr1 + '\'' +
                '}';
    }
}
