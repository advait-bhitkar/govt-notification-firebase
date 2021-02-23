package com.pribha.govtnotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.os.Bundle;
import android.widget.TextView;

public class NotificationDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);

        initComponent();
    }


    private void initComponent(){

        String titleText = getIntent().getStringExtra("title");
        String descriptionText = getIntent().getStringExtra("description");


        TextView title = findViewById(R.id.title);
        TextView description = findViewById(R.id.description);

        title.setText(titleText);
        description.setText(HtmlCompat.fromHtml(descriptionText, HtmlCompat.FROM_HTML_MODE_COMPACT));
    }

}