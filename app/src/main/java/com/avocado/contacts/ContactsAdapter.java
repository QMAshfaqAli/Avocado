package com.avocado.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.List;

public class ContactsAdapter extends ArrayAdapter<Contact>{

    ImageLoader loader = ImageLoader.getInstance();
    DisplayImageOptions options;
    LayoutInflater inflater;

    public ContactsAdapter(Context context, List<Contact> objects) {
        super(context, 0, objects);
        initOptions();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact aContact = getItem(position);

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_list, null);

            holder = new ViewHolder();
            holder.ivThumb = (ImageView) convertView.findViewById(R.id.ivThumb);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvContact = (TextView) convertView.findViewById(R.id.tvContact);
            holder.tvEmail = (TextView) convertView.findViewById(R.id.tvEmail);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvName.setText(aContact.getName());
        holder.tvContact.setText(aContact.getContact());
        holder.tvEmail.setText(aContact.getEmail());
        String tempImg = aContact.getThumb();
        if(tempImg != "") {
            loader.displayImage(tempImg, holder.ivThumb, options);
        }
        return convertView;
    }

    public static class ViewHolder {
        public ImageView ivThumb;
        public TextView tvName;
        public TextView tvContact;
        public TextView tvEmail;
    }

    private void initOptions() {
        loader.init(getConfiguration());

        options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(55))
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launch)
                .showImageOnFail(R.drawable.ic_logo)
                .resetViewBeforeLoading(false).cacheInMemory(true)
                .cacheOnDisk(true).build();

    }

    private ImageLoaderConfiguration getConfiguration() {
        File cacheDir = StorageUtils.getCacheDirectory(getContext());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getContext()).diskCacheExtraOptions(480, 800, null)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCache(new UnlimitedDiscCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024).build();
        return config;
    }
}
