package ru.nikitasemiklit.diploma.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.model.Section;

public class SectionView extends LinearLayout {

    TextView title;
    TextView desc;
    View delete;

    public SectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.list_item_title_desc, this);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.value);
        delete = findViewById(R.id.delete);
    }

    public void setSection(Section section) {
        title.setText(section.getTitle());
        desc.setText(section.getDesc());
    }

    public void setOnDeleteClickListener (OnClickListener listener){
        delete.setOnClickListener(listener);
        delete.setVisibility(VISIBLE);
    }
}
