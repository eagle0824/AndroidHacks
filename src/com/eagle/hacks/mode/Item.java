
package com.eagle.hacks.mode;

public class Item {
    private String mTitle;
    private String mPackage;
    private String mClass;
    private boolean mEnabled = true;

    public Item(Buidler builder) {
        mTitle = builder.title;
        mPackage = builder.packageName;
        mClass = builder.className;
        mEnabled = builder.enable;
    }

    public Item(String className) {
        mClass = className;
    }

    public String getClassName() {
        return mClass;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getPackage() {
        return mPackage;
    }

    public void setPackage(String packageName) {
        this.mPackage = packageName;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Item title : ").append(mTitle);
        sb.append(" class : ").append(mClass);
        sb.append(" package : ").append(mPackage);
        sb.append(" enabled : ").append(mEnabled);
        return sb.toString();
    }

    public static class Buidler {
        private String className;
        private String packageName;
        private String title;
        private boolean enable = true;

        public Buidler(String className) {
            this.className = className;
        }

        public Buidler setPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Buidler setTitle(String title) {
            this.title = title;
            return this;
        }

        public Buidler setEnabled(boolean enabled) {
            this.enable = enabled;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }
}
