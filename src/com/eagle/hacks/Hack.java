
package com.eagle.hacks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.UpdateAppearance;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

//import com.android.internal.util.XmlUtils;
import com.eagle.hacks.mode.Item;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
//import java.io.InputStream;
import java.util.ArrayList;

public class Hack extends Activity implements OnItemClickListener {

    private static final String TAG = Hack.class.getSimpleName();
    private ListView mList;
    private ItemAdapter mAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hack);
        mContext = this;
        mList = (ListView) findViewById(R.id.list);
        mAdapter = new ItemAdapter(mContext);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(this);
        loadAllItems();
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Utils.logd(TAG, "display : " + display);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = (Item) parent.getItemAtPosition(position);
        Utils.logd(TAG, item.toString());
        startActivity(item);
    }

    private void startActivity(Item item) {
        String className = item.getClassName();
        String packageName = item.getPackage();
        Intent intent = new Intent();
        intent.setClassName(TextUtils.isEmpty(packageName) ? getPackageName() : packageName,
                className);
        mContext.startActivity(intent);
    }

    private class ItemAdapter extends BaseAdapter {

        private ArrayList<Item> mItems;
        private Context mContext;
        private LayoutInflater mInflater;

        public ItemAdapter(Context context) {
            mContext = context;
            mItems = new ArrayList<Item>();
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addItem(Item item) {
            mItems.add(item);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Item getItem(int index) {
            return mItems.get(index);
        }

        @Override
        public long getItemId(int id) {
            return id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.simple_list_item_1,
                        null);
                holder = new Holder();
                holder.title = (TextView) convertView.findViewById(R.id.text1);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.title.setText(getItem(position).getTitle());
            return convertView;
        }
    }

    private static class Holder {
        public TextView title;

        public Holder() {
        }
    }

//    public void loadAllItems() {
//        try {
//            XmlPullParser parser = getResources().getXml(R.xml.item_infos);
//            AttributeSet attrs = Xml.asAttributeSet(parser);
//            XmlUtils.beginDocument(parser, Utils.TAG_ITEMS);
//            final int depth = parser.getDepth();
//            int type;
//            while (((type = parser.next()) != XmlPullParser.END_TAG || parser
//                    .getDepth() > depth) && type != XmlPullParser.END_DOCUMENT) {
//                if (type != XmlPullParser.START_TAG) {
//                    continue;
//                }
//                final String name = parser.getName();
//                TypedArray a = obtainStyledAttributes(attrs, R.styleable.Item);
//                if (Utils.TAG_ITEM.equals(name)) {
//                    String packageName = a
//                            .getString(R.styleable.Item_packageName);
//                    String className = a
//                            .getString(R.styleable.Item_className);
//                    boolean enabled = a.getBoolean(R.styleable.Item_enabled, true);
//                    CharSequence title = a.getString(R.styleable.Item_title);
//                    if (enabled) {
//                        Item item = new Item.Buidler(className).setPackageName(packageName)
//                                .setTitle(title.toString()).build();
//                        //Utils.logd(TAG, item.toString());
//                        mAdapter.addItem(item);
//                    }
//                }
//                a.recycle();
//            }
//            mAdapter.notifyDataSetChanged();
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private String getStringByName(String name){
        if(name.startsWith("@")){
            return mContext.getResources().getString(Integer.valueOf(name.replace("@", "")));
        } else {
            return name;
        }
    }

    public void loadAllItems() {
        try {
            XmlPullParser parser = getResources().getXml(R.xml.item_infos);
            int eventType = parser.getEventType();
            String className = null;
            String packageName = null;
            String title = null;
            boolean enabled = true;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
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
                                    title = getStringByName(title);
                                } else if (attrName.equals(Utils.ITEM_ATTR_ENABLED)) {
                                    enabled = Boolean.valueOf(parser.getAttributeValue(i));
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals(Utils.TAG_ITEM)) {
                            if (!TextUtils.isEmpty(className) && enabled) {
                                if (enabled) {
                                    Item item = new Item.Buidler(className).setPackageName(packageName)
                                            .setTitle(title.toString()).build();
                                    //Utils.logd(TAG, item.toString());
                                    mAdapter.addItem(item);
                                }
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
            mAdapter.notifyDataSetChanged();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
