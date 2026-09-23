package com.example.homeshine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ServicesFragment extends Fragment {

    private final String[] names = {
            "Home Cleaning", "Kitchen", "Bathroom", "Deep Cleaning", "Sofa & Carpet",
            "Laundry", "Gardening", "Pest Control", "Painting", "AC Service"
    };
    private final String[] emojis = {"🏠", "🍳", "🚿", "✨", "🛋️", "🧺", "🌿", "🐜", "🎨", "❄️"};
    private final String[] prices = {
            "from $18", "from $12", "from $10", "from $35", "from $22",
            "from $9", "from $20", "from $25", "from $40", "from $30"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_services, container, false);
        LinearLayout grid = root.findViewById(R.id.servicesGrid);

        for (int i = 0; i < names.length; i++) {
            final int index = i;

            LinearLayout row = new LinearLayout(requireContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setBackgroundResource(R.drawable.bg_card_stroke);
            int pad = dp(14);
            row.setPadding(pad, pad, pad, pad);
            LinearLayout.LayoutParams rowLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rowLp.bottomMargin = dp(10);
            row.setLayoutParams(rowLp);
            row.setGravity(android.view.Gravity.CENTER_VERTICAL);

            LinearLayout iconBg = new LinearLayout(requireContext());
            iconBg.setBackgroundResource(R.drawable.bg_icon_sage);
            iconBg.setGravity(android.view.Gravity.CENTER);
            iconBg.setLayoutParams(new LinearLayout.LayoutParams(dp(44), dp(44)));

            TextView icon = new TextView(requireContext());
            icon.setText(emojis[i]);
            icon.setTextSize(20);
            iconBg.addView(icon);

            LinearLayout textCol = new LinearLayout(requireContext());
            textCol.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams textLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            textLp.leftMargin = dp(12);
            textCol.setLayoutParams(textLp);

            TextView title = new TextView(requireContext());
            title.setText(names[i]);
            title.setTextColor(getResources().getColor(R.color.ink));
            title.setTypeface(null, android.graphics.Typeface.BOLD);
            title.setTextSize(14);

            TextView price = new TextView(requireContext());
            price.setText(prices[i]);
            price.setTextColor(getResources().getColor(R.color.teal));
            price.setTextSize(12);

            textCol.addView(title);
            textCol.addView(price);

            TextView arrow = new TextView(requireContext());
            arrow.setText("›");
            arrow.setTextSize(18);
            arrow.setTextColor(getResources().getColor(R.color.gray_text));

            row.addView(iconBg);
            row.addView(textCol);
            row.addView(arrow);

            row.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), BookingActivity.class);
                intent.putExtra("service_name", names[index]);
                intent.putExtra("service_emoji", emojis[index]);
                intent.putExtra("service_price", prices[index]);
                startActivity(intent);
            });

            grid.addView(row);
        }

        return root;
    }

    private int dp(int value) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(value * density);
    }
}
