
package com.eagle.hacks.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;

import com.eagle.hacks.R;
import com.eagle.hacks.Utils;
import com.eagle.hacks.mode.Item;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlParserActivity extends BaseActivity {

    private static final String TAG = XmlParserActivity.class.getSimpleName();

    private static final String XML_DEMO = "xml_demos.xml";
    private TextView mSaxText;
    private TextView mDomText;
    private TextView mPullText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_parser);
        mSaxText = (TextView) findViewById(R.id.sax_text);
        mDomText = (TextView) findViewById(R.id.dom_text);
        mPullText = (TextView) findViewById(R.id.pull_text);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onDomClick(View view) {
        mHandler.obtainMessage(EVENT_PARSER_XML, TYPE_DOM, 0).sendToTarget();
    }

    public void onSaxClick(View view) {
        mHandler.obtainMessage(EVENT_PARSER_XML, TYPE_SAX, 0).sendToTarget();
    }

    public void onPullClick(View view) {
        mHandler.obtainMessage(EVENT_PARSER_XML, TYPE_PULL, 0).sendToTarget();
    }

    private static final int EVENT_PARSER_XML = 1;
    private static final int EVENT_UPDATE_CONTENT = 2;

    private static final int TYPE_SAX = 1;
    private static final int TYPE_DOM = 2;
    private static final int TYPE_PULL = 3;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_PARSER_XML: {
                    ArrayList<Item> mItems = null;
                    switch (msg.arg1) {
                        case TYPE_SAX:
                            mItems = doSAXParser();
                            break;
                        case TYPE_DOM:
                            mItems = doDOMParser();
                            break;
                        case TYPE_PULL:
                            mItems = doPullParser();
                            break;
                    }
                    if (mItems != null) {
                        mHandler.obtainMessage(EVENT_UPDATE_CONTENT, msg.arg1, 0, mItems)
                                .sendToTarget();
                    }
                }
                    break;
                case EVENT_UPDATE_CONTENT: {
                    ArrayList<Item> items = (ArrayList<Item>) msg.obj;
                    switch (msg.arg1) {
                        case TYPE_SAX:
                            mSaxText.setText(buildString(items));
                            break;
                        case TYPE_DOM:
                            mDomText.setText(buildString(items));
                            break;
                        case TYPE_PULL:
                            mPullText.setText(buildString(items));
                            break;
                    }
                }
                    break;
            }
        }
    };

    private String buildString(ArrayList<Item> items) {
        StringBuilder sBuilder = new StringBuilder();
        int size = items.size();
        for (int i = 0; i < size; i++) {
            sBuilder.append(i).append(". ").append(items.get(i).toString())
                    .append("\n");
        }
        return sBuilder.toString();
    }

    private class SaxHandler extends DefaultHandler {
        private ArrayList<Item> items;
        private String className;
        private String packageName;
        private String title;
        private boolean enabled = true;

        public SaxHandler() {
            super();
        }

        private void reset() {
            className = null;
            packageName = null;
            title = null;
            enabled = true;
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (localName.equals(Utils.TAG_ITEM)) {
                int length = attributes.getLength();
                String attrName = "";
                for (int i = 0; i < length; i++) {
                    attrName = attributes.getLocalName(i);
                    if (attrName.equals(Utils.ITEM_ATTR_CLASS)) {
                        className = attributes.getValue(i);
                    } else if (attrName.equals(Utils.ITEM_ATTR_PACKAGE)) {
                        packageName = attributes.getValue(i);
                    } else if (attrName.equals(Utils.ITEM_ATTR_TITLE)) {
                        title = attributes.getValue(i);
                    } else if (attrName.equals(Utils.ITEM_ATTR_ENABLED)) {
                        enabled = Boolean.valueOf(attributes.getValue(i));
                    }
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (!TextUtils.isEmpty(className)) {
                Item item = new Item(className);
                if (!TextUtils.isEmpty(packageName)) {
                    item.setPackage(packageName);
                }
                if (!TextUtils.isEmpty(title)) {
                    item.setTitle(title);
                }
                item.setEnabled(enabled);
                items.add(item);
            }
            reset();
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            items = new ArrayList<Item>();
        }

        public ArrayList<Item> getItems() {
            return items;
        }
    };

    private ArrayList<Item> doSAXParser() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            SaxHandler handler = new SaxHandler();
            InputStream is = getResources().getAssets().open(XML_DEMO);
            parser.parse(is, handler);
            return handler.getItems();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<Item>();
    }

    private ArrayList<Item> doDOMParser() {
        ArrayList<Item> items = new ArrayList<Item>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = getResources().getAssets().open(XML_DEMO);
            Document doc = builder.parse(is);
            Element rootElement = doc.getDocumentElement();
            NodeList list = rootElement.getElementsByTagName(Utils.TAG_ITEM);
            Node node = null;
            Node subNode = null;
            String attrName = "";
            String className = null;
            String packageName = null;
            String title = null;
            boolean enabled = true;
            for (int i = 0; i < list.getLength(); i++) {
                node = list.item(i);
                for(int j=0;j<node.getAttributes().getLength();j++){
                    subNode = node.getAttributes().item(j);
                    attrName = subNode.getNodeName();
                    if (attrName.endsWith(Utils.ITEM_ATTR_CLASS)) {
                        className = subNode.getNodeValue();
                    } else if (attrName.endsWith(Utils.ITEM_ATTR_PACKAGE)) {
                        packageName =subNode.getNodeValue();;
                    } else if (attrName.endsWith(Utils.ITEM_ATTR_TITLE)) {
                        title = subNode.getNodeValue();
                    } else if (attrName.endsWith(Utils.ITEM_ATTR_ENABLED)) {
                        enabled = Boolean.valueOf(subNode.getNodeValue());
                    }
                }
                if (!TextUtils.isEmpty(className)) {
                    Item item = new Item(className);
                    if (!TextUtils.isEmpty(packageName)) {
                        item.setPackage(packageName);
                    }
                    if (!TextUtils.isEmpty(title)) {
                        item.setTitle(title);
                    }
                    item.setEnabled(enabled);
                    items.add(item);
                }
                className = null;
                packageName = null;
                title = null;
                enabled = true;
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return items;
    }

    private ArrayList<Item> doPullParser() {
        ArrayList<Item> items = new ArrayList<Item>();
        // XmlPullParserFactory factory;
        try {
            // factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = Xml.newPullParser();
            InputStream is = getResources().getAssets().open(XML_DEMO);
            parser.setInput(is, "utf-8");
            int eventType = parser.getEventType();
            String className = null;
            String packageName = null;
            String title = null;
            boolean enabled = true;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        items = new ArrayList<Item>();
                        break;
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals(Utils.TAG_ITEM)) {
                            int count = parser.getAttributeCount();
                            String attrName = "";
                            for (int i = 0; i < count; i++) {
                                attrName = parser.getAttributeName(i);
                                if (attrName.equals(Utils.ITEM_ATTR_CLASS)) {
                                    className = parser.getAttributeValue(i);
                                } else if (attrName.equals(Utils.ITEM_ATTR_PACKAGE)) {
                                    packageName = parser.getAttributeValue(i);
                                } else if (attrName.equals(Utils.ITEM_ATTR_TITLE)) {
                                    title = parser.getAttributeValue(i);
                                } else if (attrName.equals(Utils.ITEM_ATTR_ENABLED)) {
                                    enabled = Boolean.valueOf(parser.getAttributeValue(i));
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals(Utils.TAG_ITEM)) {
                            if (!TextUtils.isEmpty(className)) {
                                Item item = new Item(className);
                                if (!TextUtils.isEmpty(packageName)) {
                                    item.setPackage(packageName);
                                }
                                if (!TextUtils.isEmpty(title)) {
                                    item.setTitle(title);
                                }
                                item.setEnabled(enabled);
                                items.add(item);
                            }
                            className = null;
                            packageName = null;
                            title = null;
                            enabled = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
