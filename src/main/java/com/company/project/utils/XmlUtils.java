package com.company.project.utils;

import com.company.project.system.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhaoh
 * @version V1.0
 * @Title:
 * @Description:
 * @Company: 北京紫光华宇软件股份有限公司
 * @date 2014-11-21
 */
public class XmlUtils {
    private static final Logger logger = LoggerFactory.getLogger(XmlUtils.class);

    /**
     * xml字符串转为document
     *
     * @param xmlStr
     * @return
     */
    public static Document stringToXml(String xmlStr) {
        if (StringUtils.isBlank(xmlStr)) {
            return null;
        }
        SAXReader saxReader = new SAXReader();
        Document doc = null;
        try {
            doc = saxReader.read(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
            return doc;
        } catch (UnsupportedEncodingException e) {
            logger.error("Document字符编码出错：UTF-8");
            return null;
        } catch (DocumentException e) {
            logger.error("String 转Document出错：" + xmlStr);
            return null;
        }
    }

    /**
     * 返回符合该xpath的单一节点的的值，如有多个匹配，则返回第一个
     *
     * @param elem
     * @param xpath
     * @return
     */
    public static String getSingleValue(Element elem, String xpath) {
        if (elem == null || StringUtils.isBlank(xpath)) {
            return null;
        }
        Node node = elem.selectSingleNode(xpath);
        if (null == node) {
            return null;
        }
        return node.getStringValue();
    }

    /**
     * 获取节点的value值
     *
     * @param elem
     * @return
     */
    public static String getValue(Element elem) {
        Node node = elem.selectSingleNode("@value");
        if (null == node) {
            return null;
        }
        return node.getStringValue();
    }

    /**
     * 返回符合该xpath的单一节点的value属性的值，如有多个匹配，则返回第一个
     *
     * @param doc
     * @param xpath
     * @return
     */
    public static String getSingleValue(Document doc, String xpath) {
        if (doc == null || StringUtils.isBlank(xpath)) {
            return null;
        }
        Node node = doc.selectSingleNode(xpath);
        if (null == node) {
            return null;
        }
        return node.getStringValue();
    }

    /**
     * 获取所有符合xpath的节点的value属性，用“##”分隔返回
     *
     * @param node
     * @param xpath
     * @return
     */
    public static String getValues(Element node, String xpath) {
        if (node == null || StringUtils.isBlank(xpath)) {
            return null;
        }
        @SuppressWarnings("unchecked")
        List<Node> els = node.selectNodes(xpath);
        if (CollectionUtils.isEmpty(els)) {
            return null;
        } else if (els.size() == 1) {
            return els.get(0).getStringValue();
        } else {
            StringBuilder sb = new StringBuilder();
            for (Node el : els) {

                sb.append(el.getStringValue()).append(Constants.char_separator);
            }
            return sb.substring(0, sb.length() - 2);
        }
    }

    /**
     * 获取所有符合xpath的节点的value属性，用“,”分隔返回
     *
     * @param doc
     * @param xpath
     * @return
     */
    public static String getValues(Document doc, String xpath) {
        if (null == doc) {
            return null;
        } else {
            return getValues(doc.getRootElement(), xpath);
        }
    }

    /**
     * 获取所有符合该xpath的元素集合
     *
     * @param elem
     * @param xpath
     * @return
     */
    public static List<?> getList(Element elem, String xpath) {
        if (elem == null || StringUtils.isBlank(xpath)) {
            return new ArrayList<Object>();
        }
        List<?> lst = elem.selectNodes(xpath);
        return lst;
    }

    /**
     * 获取所有符合该xpath的元素集合
     *
     * @param doc
     * @param xpath
     * @return
     */
    public static List<?> getList(Document doc, String xpath) {
        if (doc == null || StringUtils.isBlank(xpath)) {
            return new ArrayList<Object>();
        }
        List<?> lst = doc.selectNodes(xpath);
        return lst;
    }

    /**
     * 获取副歌该xpath的单一元素，如有多个匹配，则返回第一个
     *
     * @param elem
     * @param xpath
     * @return
     */
    public static Node getSingleNode(Element elem, String xpath) {
        if (elem == null || StringUtils.isBlank(xpath)) {
            return null;
        }
        return elem.selectSingleNode(xpath);
    }

    /**
     * 获取副歌该xpath的单一元素，如有多个匹配，则返回第一个
     *
     * @param elem
     * @param xpath
     * @return
     */
    public static Element getSingleElement(Element elem, String xpath) {
        if (elem == null || StringUtils.isBlank(xpath)) {
            return null;
        }
        return (Element) elem.selectSingleNode(xpath);
    }

    public static Document readXml(File file) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            logger.error("读取xml错误 :{}", file.getAbsolutePath());
            logger.error("堆栈信息:", e);
            return null;
        }
        return document;
    }

    public static Document readXml(InputStream is) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(is);
        } catch (DocumentException e) {
            logger.error("读取xml错误");
            logger.error("堆栈信息:", e);
            return null;
        }
        return document;
    }

    public static void WriteXml(String path, Document doc) {
        if (null == doc) {
            return;
        }
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        XMLWriter writer = null;
        try {
            fos = new FileOutputStream(path);
            osw = new OutputStreamWriter(fos, "utf-8");
            OutputFormat of = new OutputFormat();
            of.setEncoding("utf-8");
            of.setIndent(true);
            of.setIndent("    ");
            of.setNewlines(true);
            writer = new XMLWriter(osw, of);
            writer.write(doc);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            logger.error("Document字符编码出错：UTF-8");
            logger.error("堆栈信息:", e);
        } catch (FileNotFoundException e) {
            logger.error("FileNotFound!: {}", path);
            logger.error("堆栈信息:", e);
        } catch (IOException e) {
            logger.error("写xml文件错误！: {}", path);
            logger.error("堆栈信息:", e);
        } finally {
            IOUtils.closeQuietly(osw);
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                }
            }
        }

    }

    public static void WriteXml(String path, String xmlStr) {
        Document doc = stringToXml(xmlStr);
        WriteXml(path, doc);
    }

    public static boolean removeAttribute(Element e, String xpath, Object obj) {
        List<String> lst = new ArrayList<String>();
        if (obj instanceof String) {
            lst.add((String) obj);
        } else if (obj instanceof List) {
            List<?> tList = (List<?>) obj;
            for (Object tObj : tList) {
                if (tObj instanceof String) {
                    lst.add((String) tObj);
                }
            }
        }

        List<?> loopList = e.selectNodes(xpath);
        while (loopList.size() > 0) {
            String targetXPath = xpath;
            Iterator<?> tarIt = e.selectNodes(targetXPath).iterator();
            while (tarIt.hasNext()) {
                Element tarNode = (Element) tarIt.next();
                for (String str : lst) {
                    Attribute attr = tarNode.attribute(str);
                    if (attr != null) {
                        tarNode.remove(attr);
                    }
                }
            }
            loopList = e.selectNodes(targetXPath);
        }
        return true;
    }

    public static boolean cleanOValue(Element e) {
        String ovalueXpath = "//*[@oValue]";
        String ovalueAttr = "oValue";
        String sXpath = "//*[@s]";
        String sAttr = "s";
        return removeAttribute(e, ovalueXpath, ovalueAttr) && removeAttribute(e, sXpath, sAttr);
    }

    public static boolean cleanNameCN(Element e) {
        String xpath = "//*[@nameCN]";
        String attr = "nameCN";
        return removeAttribute(e, xpath, attr);
    }

    public static String cleanNameCN(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        String nameCN = "\"@nameCN\"[\\s]*:[\\s]*\"[^\"]*\",?";
        String jStr = json.replaceAll(nameCN, "");
        ;
        return jStr;
    }

    public static boolean cleanOValueAndNameCN(Element e) {
        // 删除所有ovalue属性以及nameCN属性
        String xpath = "//*[@oValue or @nameCN]";
        List<String> attList = new ArrayList<String>();
        attList.add("oValue");
        attList.add("nameCN");
        return removeAttribute(e, xpath, attList);
    }

    public static boolean deleteNode(Element e, String xpath) {
        List<?> loopList = e.selectNodes(xpath);
        while (loopList.size() > 0) {
            String targetXPath = xpath;
            Iterator<?> tarIt = e.selectNodes(targetXPath).iterator();
            while (tarIt.hasNext()) {
                Element tarNode = (Element) tarIt.next();
                tarNode.detach();
            }
            loopList = e.selectNodes(xpath);
        }
        return true;
    }

    public static boolean cleanNonNode(Element e) {
        // 清除没有子节点且本身没有value属性或者value属性为空的节点
        String xpath = "//*[not(*)][not(@value) or @value='']";
        return deleteNode(e, xpath);
    }

    public static Element getElementFromInputStream(InputStream inputStream) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(inputStream);
        } catch (DocumentException e) {
            logger.error("读取文件流异常");
        }
        // 得到xml根元素
        return document != null ? document.getRootElement() : null;
    }

    @SuppressWarnings("unchecked")
    /**
     * 将String类型的xml转换成对象
     */
    public static Object convertXmlStrToObject(Class clazz, String xmlStr) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }

    @SuppressWarnings("unchecked")
    /**
     * 将file类型的xml转换成对象
     */
    public static Object convertXmlFileToObject(Class clazz, String xmlPath) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileReader fr = null;
            try {
                fr = new FileReader(xmlPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            xmlObject = unmarshaller.unmarshal(fr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }

    public static List<String> getListValues(Element elem, String xpath) {
        List<String> values = new ArrayList<String>();
        if (elem == null || StringUtils.isBlank(xpath)) {
            return values;
        }
        @SuppressWarnings("unchecked")
        List<Attribute> lst = elem.selectNodes(xpath);
        if (!CollectionUtils.isEmpty(lst)) {
            for (Attribute el : lst) {
                values.add(el.getText());
            }
        }
        return values;
    }
}
