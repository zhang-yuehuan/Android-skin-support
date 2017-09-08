package theme.support.demo.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import theme.support.demo.BaseActivity;
import theme.support.demo.R;

public class BasicWidgetActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_widget);
        initToolbar();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final CharSequence[] entries = getResources().getStringArray(R.array.languages);
        final ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, R.layout.simple_list_item, entries);
        adapter.setDropDownViewResource(R.layout.simple_list_item);
        spinner.setAdapter(adapter);

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.auto);
        String[] arrA = {"aa", "aab", "aac"};
        ArrayAdapter<String> arrayAdapterA = new ArrayAdapter<String>(this, R.layout.simple_list_item, arrA);
        autoCompleteTextView.setAdapter(arrayAdapterA);
        autoCompleteTextView.setThreshold(1);

        MultiAutoCompleteTextView multiAutoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.multi_auto);
        String[] arrB = {"bb", "bbc", "bbd,cc"};
        ArrayAdapter<String> arrayAdapterB = new ArrayAdapter<String>(this, R.layout.simple_list_item, arrB);
        multiAutoCompleteTextView.setAdapter(arrayAdapterB);
        multiAutoCompleteTextView.setThreshold(1);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}
