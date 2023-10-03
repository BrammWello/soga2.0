package com.devbramm.soga.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.devbramm.soga.R;

public class OnboardingAdapter extends PagerAdapter {

    private Context context;

    public OnboardingAdapter(Context context) {
        this.context = context;
    }

    private int[] slider_images = {
            R.drawable.undraw_mobile_re_q4nk,
            R.drawable.undraw_mobile_inbox_re_ciwq,
            R.drawable.undraw_mobile_messages_re_yx8w,
            R.drawable.undraw_mobile_feed_re_72ta
    };

    private String[] slider_title = {
            "The Soga App",
            "Communication Redefined",
            "Catch up on the Go",
            "Message Privately"
    };

    private String[] slider_desc = {
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
    };

    @Override
    public int getCount() {
        return slider_title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.util_onboarding_screen, container, false);

        ImageView img_banner = view.findViewById(R.id.img_banner);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_desc = view.findViewById(R.id.tv_desc);

        img_banner.setImageResource(slider_images[position]);
        tv_title.setText(slider_title[position]);
        tv_desc.setText(slider_desc[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
